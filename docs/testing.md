# Тестирование приложения Foodie

[Home](index.md)    
[Функциональные требования](functionalRequirements.md)  
[Спецификация](specification.md)  
[Диаграмма файлов приложения](filesSchema.md)   
[Дополнительная спецификация](dopSpecification.md)  
[Схема базы данных](databaseSchema.md)        
[Презентация проекта](projectPresentation.md)  
[Тестирование](testing.md)

## Описание

Проект включает в себя набор unit и UI тестов для iOS приложения Foodie. Основная цель тестов — проверка корректности работы различных функциональных компонентов приложения, таких как карта, база данных, настройки, поиск, регистрация, оплата и вход в систему.

## Покрытие тестами

Покрытие тестами составляет 95%, что гарантирует высокую надёжность и устойчивость приложения.

![coverage](https://github.com/fpmi-tp2024/tpmp-rvn-lab10-libra/blob/pages/img/tests/coverage.png?raw=true)

## Примечание

Из-за специфики приложения, созданного на SwiftUI, было уделено больше внимания UI тестам. Это позволяет более детально проверить взаимодействие пользователя с интерфейсом и обеспечить стабильную работу приложения.

## Содержание
- [MapManagerTests](#mapmanagertests)
- [SettingTests](#settingtests)
- [SearchTests](#searchtests)
- [RegistrationScreenTests](#registrationscreentests)
- [PaymentTests](#paymenttests)
- [LoginScreenTests](#loginscreentests)

## MapManagerTests

Файл: `PaymentTests.swift`

Тестирует функционал поиска ближайших ресторанов на карте.

## SettingTests

Файл: `SettingTests.swift`

Тестирует изменение темы оформления, обновление профиля пользователя и обработку ошибок при обновлении.

## SearchTests

Файл: `SearchTests.swift`

Тестирует поиск по названию блюд.

## RegistrationScreenTests

Файл: `RegistrarionScreenTests.swift`

Тестирует успешную регистрацию, регистрацию с несовпадающими паролями и обработку ошибок при регистрации.

## PaymentTests

Файл: `PaymentTests.swift`

Тестирует добавление блюд в корзину, оплату банковской картой и наличными.

## LoginScreenTests

Файл: `LoginScreenTests.swift`

Тестирует успешный вход в систему и вход с неверными данными.
