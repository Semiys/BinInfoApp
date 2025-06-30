# BIN Info App

Это приложение - тестовое задание для стажера Android-разработчика. Оно позволяет пользователю получить информацию о банковской карте по её BIN (Bank Identification Number).

## 🎥 Видео Демонстрация

[https://github.com/user-attachments/assets/7c6a5da2-a702-4b2e-abd4-9ca7cc90a91e]

## 🛠️ Технологический стек и архитектура

*   **Язык:** [Kotlin](https://kotlinlang.org/)
*   **Асинхронность:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) - современный декларативный UI-тулкит.
*   **Архитектура:** MVVM + [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) (разделение на слои data, domain, presentation).
*   **Dependency Injection:** [Hilt](https://dagger.dev/hilt/) - для внедрения зависимостей.
*   **Навигация:** [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - для перемещения между экранами.
*   **Работа с сетью:** [Retrofit 2](https://square.github.io/retrofit/) - для выполнения HTTP-запросов к API.
*   **Локальное хранилище:** [Room](https://developer.android.com/training/data-storage/room) - для сохранения истории запросов.
*   **Сборка:** Gradle с использованием [Version Catalog](https://docs.gradle.org/current/userguide/version_catalogues.html) для управления зависимостями.

## 🌐 API

Приложение использует публичное API [binlist.net](httpshttps://binlist.net/) для получения информации о BIN.

## 🏗️ Структура проекта

Проект построен в соответствии с принципами чистой архитектуры и разделен на три основных модуля (слоя):

*   **`:domain`**: Содержит бизнес-логику приложения, модели данных, репозитории (в виде интерфейсов) и Use-Cases. Этот слой не зависит ни от каких других слоев.
*   **`:data`**: Реализует интерфейсы репозиториев из domain-слоя. Отвечает за получение данных из сети (Retrofit) и локальной базы данных (Room), а также за маппинг данных.
*   **`:presentation` (app)**: Отвечает за отображение данных на UI (Jetpack Compose). Содержит ViewModels, которые взаимодействуют с Use-Cases из domain-слоя.

## 🚀 Как собрать и запустить

1.  Клонируйте репозиторий: `git clone https://github.com/ваш-логин/ваш-репозиторий.git`
2.  Откройте проект в Android Studio.
3.  Дождитесь синхронизации Gradle.
4.  Запустите приложение на эмуляторе или физическом устройстве.

---
Готово к проверке! 
