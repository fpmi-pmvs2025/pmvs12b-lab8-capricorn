## Description

[Home](index.md)    
[Функциональные требования](functionalRequirements.md)  
[Спецификация](specification.md)  
[Диаграмма файлов приложения](filesSchema.md)   
[Схема базы данных](databaseSchema.md)          
[Презентация проекта](projectPresentation.md)       
[Тестирование](testing.md)

# Description

Memory Card Game — это Android-приложение, разработанное с использованием Jetpack Compose, которое предлагает увлекательный способ тренировать визуальную память. Игровой процесс основан на классической механике: игроку необходимо находить пары одинаковых карт, переворачивая их по две за раз.

Приложение поддерживает различные конфигурации сложности — пользователь может выбирать количество карт для игры, а после завершения партии сохраняется подробная статистика: общее время прохождения, количество попыток и дата начала игры. Вся эта информация сохраняется локально с помощью Room, что позволяет игроку отслеживать свой прогресс и улучшать результаты со временем.

Также приложение предлагает гибкие настройки внешнего вида: доступен выбор между светлой или тёмной темой оформления. Все пользовательские предпочтения сохраняются с использованием DataStore, что гарантирует комфортное взаимодействие с приложением на протяжении всего времени использования.

Memory Card Game — это не просто игра, а тренажёр для ума, выполненный в духе современных Android-приложений с упором на производительность, плавность интерфейса и чистую архитектуру.

# Overview
<table>
  <tr>
    <td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080756.png?raw=true" width="200" style="display: inline" /></td>
    <td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075800.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075825.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075859.png?raw=true" width="200" style="display: inline" /></td>


  </tr>
  <tr>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075847.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075917.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075928.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080654.png?raw=true" width="200" style="display: inline" /></td>


  </tr>

  <tr>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080002.png?raw=true"  width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080708.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_075941.png?raw=true" width="200" style="display: inline" /></td>

  </tr>

  
</table>

___

<table>
  <tr>
    <td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080028.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080047.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080056.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080109.png?raw=true" width="200" style="display: inline" /></td>



  </tr>
  <tr>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080119.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080729.png?raw=true" width="200" style="display: inline" /></td>
  </tr>
</table>

___

<table>
  <tr>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080254.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080326.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080338.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080431.png?raw=true" width="200" style="display: inline" /></td>


  </tr>
  <tr>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080403.png?raw=true" width="200" style="display: inline" /></td>
<td><img src="https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/d5131407b6bc47a4c3687d1e573d50d6a05bacad/imgs/Screenshot_20250405_080352.png?raw=true" width="200" style="display: inline" /></td>
  </tr>
</table>


# Installation
Чтобы начать работу с приложеинем, скачайте [apk-файл](https://github.com/fpmi-pmvs2025/pmvs12b-lab8-capricorn/blob/159e7c67d2ed62355c02ad6c03951f22f60026ca/app-release.apk) и установите приложение на телефон или эмулятор.


# Contributing
## Авторы проекта: **Ладик Алина, Романовец Алексей, Шейнин Василий.**

1. **Team Lead:** Шейнин Василий.
2. **Project Manager** Ладик Алина.
3. **UX/UI-Designer** Ладик Алина.
4. **Software Engineer:** Ладик Алина.
5. **DevOps & QA Engineer:** Шейнин Василий.
6. **Technical Writer:** Романовец Алексей.
