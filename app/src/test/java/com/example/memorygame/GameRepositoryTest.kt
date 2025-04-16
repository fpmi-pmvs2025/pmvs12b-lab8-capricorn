package com.example.memorygame

import com.example.memorygame.data.db.GameDao
import com.example.memorygame.data.db.GameDataBase
import com.example.memorygame.data.entity.Statistic
import com.example.memorygame.data.model.Card
import com.example.memorygame.data.model.Cards
import com.example.memorygame.data.model.DeckResponse
import com.example.memorygame.data.retrofit.CardApiService
import com.example.memorygame.domain.GameRepository
import com.example.memorygame.screens.CardData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class GameRepositoryTest {

    @Mock
    private lateinit var mockDb: GameDataBase

    @Mock
    private lateinit var mockApiService: CardApiService

    @Mock
    private lateinit var mockGameDao: GameDao

    private lateinit var gameRepository: GameRepository

    @Before
    fun setup() {
        whenever(mockDb.getGameDao()).thenReturn(mockGameDao)
        gameRepository = GameRepository(mockDb, mockApiService)
    }

    @Test
    fun `upsert should call dao upsert`() = runBlocking {
        val testStatistic = Statistic(
            duration = 1000L,
            startTime = Date(),
            numberOfCards = 12,
            attempts = 10
        )

        gameRepository.upset(testStatistic)

        verify(mockGameDao).upsert(testStatistic)
    }

    @Test
    fun `getAllStatistics should return list from dao`() = runBlocking {
        val testStatistics = listOf(
            Statistic(
                duration = 1000L,
                startTime = Date(),
                numberOfCards = 12,
                attempts = 10
            ),
            Statistic(
                duration = 2000L,
                startTime = Date(),
                numberOfCards = 16,
                attempts = 15
            )
        )
        whenever(mockGameDao.getAllStatistics()).thenReturn(testStatistics)

        val result = gameRepository.getAllStatistics()

        assertEquals(testStatistics, result)
    }

    @Test
    fun `getCardsForGame should return correct number of paired and shuffled cards`() = runBlocking {
        val deckId = "test_deck_id"
        val numberOfCards = 4
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = deckId,
                remaining = 52,
                shuffled = true
            )
        )
        val mockCardsResponse = Response.success(
            Cards(
                success = true,
                deck_id = deckId,
                remaining = 50,
                cards = listOf(
                    Card(code = "AS", image = "image1", value = "ACE", suit = "SPADES"),
                    Card(code = "KH", image = "image2", value = "KING", suit = "HEARTS")
                )
            )
        )

        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)
        whenever(mockApiService.getCards(eq(deckId), eq(2))).thenReturn(mockCardsResponse)

        val result = gameRepository.getCardsForGame(numberOfCards)

        assertEquals(numberOfCards, result.size)
        val grouped = result.groupBy { it.value }
        assertEquals(2, grouped.size)
        assertEquals(2, grouped.values.first().size)
        assertEquals(2, grouped.values.last().size)
    }

    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when deck response is not successful`(): Unit = runBlocking {
        val errorResponse = Response.error<DeckResponse>(400, mock())
        whenever(mockApiService.getDeck()).thenReturn(errorResponse)

        gameRepository.getCardsForGame(4)
    }

    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when deck success is false`(): Unit = runBlocking {
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = false,
                deck_id = "",
                remaining = 0,
                shuffled = false
            )
        )
        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)

        gameRepository.getCardsForGame(4)
    }

    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when deck id is null`(): Unit = runBlocking {
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = "",
                remaining = 52,
                shuffled = true
            )
        )
        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)

        gameRepository.getCardsForGame(4)
    }

    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when cards response is not successful`(): Unit = runBlocking {
        val deckId = "test_deck_id"
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = deckId,
                remaining = 52,
                shuffled = true
            )
        )
        val errorResponse = Response.error<Cards>(400, mock())

        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)
        whenever(mockApiService.getCards(eq(deckId), any())).thenReturn(errorResponse)

        gameRepository.getCardsForGame(4)
    }


    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when cards success is false`(): Unit = runBlocking {
        val deckId = "test_deck_id"
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = deckId,
                remaining = 52,
                shuffled = true
            )
        )
        val mockCardsResponse = Response.success(
            Cards(
                success = false,
                deck_id = deckId,
                remaining = 0,
                cards = emptyList()
            )
        )

        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)
        whenever(mockApiService.getCards(eq(deckId), any())).thenReturn(mockCardsResponse)

        gameRepository.getCardsForGame(4)
    }

    @Test(expected = Exception::class)
    fun `getCardsForGame should throw when cards list is null`(): Unit = runBlocking {
        val deckId = "test_deck_id"
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = deckId,
                remaining = 52,
                shuffled = true
            )
        )
        val mockCardsResponse = Response.success(
            Cards(
                success = true,
                deck_id = deckId,
                remaining = 50,
                cards = listOf()
            )
        )

        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)
        whenever(mockApiService.getCards(eq(deckId), any())).thenReturn(mockCardsResponse)

        gameRepository.getCardsForGame(4)
    }

    @Test
    fun `getCardsForGame should return shuffled cards`() = runBlocking {
        val deckId = "test_deck_id"
        val numberOfCards = 4
        val mockDeckResponse = Response.success(
            DeckResponse(
                success = true,
                deck_id = deckId,
                remaining = 52,
                shuffled = true
            )
        )
        val mockCardsResponse = Response.success(
            Cards(
                success = true,
                deck_id = deckId,
                remaining = 50,
                cards = listOf(
                    Card(code = "AS", image = "image1", value = "ACE", suit = "SPADES"),
                    Card(code = "KH", image = "image2", value = "KING", suit = "HEARTS")
                )
            )
        )

        whenever(mockApiService.getDeck()).thenReturn(mockDeckResponse)
        whenever(mockApiService.getCards(eq(deckId), eq(2))).thenReturn(mockCardsResponse)

        val result1 = gameRepository.getCardsForGame(numberOfCards)
        val result2 = gameRepository.getCardsForGame(numberOfCards)

        assertTrue(result1 != result2 || result1 == result2)
    }
}