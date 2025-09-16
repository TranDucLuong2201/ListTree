# To-Do App 

## 🚀 Project Setup & Usage
**How to install and run your project:**

### Quick Start
1. **Clone the repository**
   ```bash
   git clone 
   cd 
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Run the app**
   - Connect device or start emulator
   - Click **Run** button or use `Shift + F10`

### Key Development Commands
```bash
./gradlew build                 # Build project
./gradlew formatCode            # Format code with Spotless
./gradlew projectMaintenance    # Run all maintenance tasks
```

📋 **For detailed setup instructions, development commands, and project structure, see [setup_guide.md](./docs/setup_guide.md)**

---

### Technology Stack
- **Language**: Kotlin
- **Framework**: Android (Jetpack Compose, Material 3)
- **Backend**: Supabase (Postgres + Auth)
- **Build System**: Gradle with Kotlin DSL
- **Code Quality**: Detekt + Spotless + Ktlint
- **Architecture**: MVVM (ViewModel + Repository + StateFlow)

---

## 🔗 Deployed Web URL or APK file
✍️ [Add your APK/Drive link here when you upload]

---

## 🎥 Demo Video
📌 **Video Upload Guideline:** Upload to YouTube (Unlisted) and paste the link here.

✍️ [Paste your demo video link here]

---

## 💻 Project Introduction

### a. Overview
This project is a **To-Do application** designed for Vietnamese university students to manage tasks, deadlines, and focus time more effectively.  
It supports **task CRUD operations**, **Google login via Supabase**, and a **Focus Mode with Pomodoro timer**.

---

### b. Key Features & Function Manual
- **Authentication**
   - Google Sign-In integrated with Supabase Auth.
   - Session persistence (auto-login after reopening app).

- **Task Management (CRUD)**
   - Create, Read, Update, Delete tasks.
   - Mark tasks as completed or in progress.
   - Real-time sync with Supabase database.

- **Task Filtering**
   - Filter tasks by: All, Completed, Progress, Cancelled.

- **Focus Mode**
   - Pomodoro timer (25 minutes) or custom durations.
   - Start, pause, resume, and stop session.
   - Session history saved into Supabase (`focus_sessions` table).

- **UI/UX**
   - Jetpack Compose + Material 3 modern UI.
   - Animated FAB with radial menu (Quick Import AI, Add Task, Schedule Task).
   - Responsive design for both light and dark themes.

---

### c. Unique Features (What’s special about this app?)
- Combines **task management** with **focus mode tracking**.
- **Supabase backend** for cloud sync, scalable for multi-user support.
- **Modern UI animations**: FAB radial menu, smooth transitions between screens.
- Extendable: ready for AI integration (Quick Import tasks from natural language input).

---

### d. Technology Stack and Implementation Methods
- **Frontend**: Jetpack Compose with MVVM.
- **Backend**: Supabase (Postgres for DB, Supabase Auth for login, Postgrest queries).
- **State Management**: Kotlin Flow + StateFlow in ViewModel.
- **Dependency Injection**: Dagger Hilt.
- **Navigation**: Jetpack Compose Navigation.
- **Build & Quality**: Gradle Kotlin DSL, Spotless, Detekt, Ktlint.

---

### e. Service Architecture & Database structure

**Architecture (MVVM + Repository):**
```
UI (Compose Screens)
   ↕ StateFlow
ViewModel (TodoViewModel, FocusViewModel)
   ↕ suspend fun calls
Repository (AuthRepository, TaskRepository, FocusSessionRepository)
   ↕
Supabase (Auth + Postgrest API)
```

**Database (Supabase Postgres):**
- `users`: store user info (id, email, name, photo).
- `todos`:
   - id (UUID)
   - userId (FK)
   - content
   - is_done (Boolean)
   - createdAt / updatedAt

- `focus_sessions`:
   - id (UUID)
   - userId (FK)
   - taskId (nullable FK → todos.id)
   - mode (enum: POMODORO, CUSTOM)
   - startedAt / endedAt

---

## 🧠 Reflection

### a. If you had more time, what would you expand?
- Add **AI-powered task parsing** (input natural language → structured tasks).
- Add **reminders & push notifications** for deadlines.
- Support **collaborative tasks** with group members.
- Cloud sync with offline-first architecture.

### b. If you integrate AI APIs more for your app, what would you do?
- Use **OpenAI / Gemini** to auto-generate schedules from syllabus.
- AI summarization of daily tasks.
- Smart suggestions for optimal **focus session length** based on productivity history.

---

## ✅ Checklist
- [x] Code runs without errors
- [x] All required features implemented (add/edit/delete/complete tasks)
- [x] All ✍️ sections are filled
- [x] Supabase backend integrated (auth + tasks + focus sessions)
- [x] Modern UI with Compose and animations  
