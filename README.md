[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/4ZAJL3PP)
# Laporan Akhir Final Project OOP D

## 1. Informasi Umum
* **Nama Game**: Flipsy
* **Anggota Kelompok**:
    1. Jasmine Firdhousy Muslich - 5025231051
    2. Lailatul Annisa Fitriana - 5025231202
    3. Rosidah Darman - 5025231307
    4. Dzikrina Hidayani Martin - 5025231311
* **Tech Stack**: Java

## 2. Deskripsi Game

### 2.1 Konsep Game
* **Genre**: Puzzle/Card Matching Game
* **Gameplay/Rule**: Player akan melihat kartu-kartu yang terbalik di layar. Setiap kartu punya pasangan yang serupa. Pemain harus mencocokkan dua kartu yang mirip hingga kartu habis. Jika player membalik dua kartu yang berbeda, kartu akan kembali tertutup. Jika player membalik kartu yang sama, maka kartu akan tetap terbuka.
* **Objective**: Menemukan semua pasangan kartu dalam waktu sesingkat mungkin.
* **Single/Multi Player**: Single Player

### 2.2 Fitur Utama
1. Save/Load System: fitur untuk menyimpan/melanjutkan game di lain waktu.
2. Achievement System: pemain akan mendapat achievement untuk pencapaian tertentu (Contoh: tidak pernah membuka dua kartu yang tidak cocok, menyelesaikan game dalam waktu kurang dari 20 detik untuk level pertama, kurang dari 30 detik untuk level kedua, dan kurang dari 40 detik untuk level ketiga)
3. Timer: menghitung waktu yang dihabiskan player untuk menyelesaikan permainan.
4. Level Difficulty: tingkat kesulitan yang ditentukan dengan bertambahnya jumlah kartu seiring peningkatan level/kesulitan.

## 3. Implementasi Fitur Wajib

### 3.1 Save/Load System
* **Implementasi**: menyimpan dan memuat data pemain ketika terakhir kali bermain.
* **Konsep OOP**: menggunakan class `GameState` untuk merepresentasikan informasi save/load.
* **Penerapan SOLID**:
  1. Single Responsibility Principle: class `GameState` hanya berperan untuk menyimpan informasi status permainan 
* **Design Pattern yang Digunakan**:
* **Code Snippet**:
```
// class yang merepresentasikan state permainan
// mengimplementasikan interface Serializable untuk menyimpan status permainan dalam urutan byte yang nanti dimuat/dibaca kembali dengan cara dideserialisasi
public class GameState implements Serializable {
    private int score;
    private int level;
    
    // constructor dan getter/setter
    public GameState(int score, int level) {
        this.score = score;
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

// class yang punya single responsibility menyimpan GameState
// IOException adalah exception yang terjadi saat ada masalah I/O (contoh: kesalahan saat open, read, write file)
// try-with-resources akan otomatis menutup oos setelah menulis objek ke file sebagai byte stream
public class GameStateSaver {
    public void save(GameState state, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(state);
        }
    }
}

// class yang hanya bertanggung jawab memuat GameState
public class LoadGameState {
    public GameState load(String filename) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameState) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class tidak ditemukan", e);
        }
    }
}
```

### 3.2 Achievement System
* **Jenis Achievement**:
    1. Champion: menyelesaikan seluruh level yang ada.
    2. Einstein: menyelesaikan level tanpa membuka kartu yang salah.
    3. Turbo: menyelesaikan game di bawah 20 detik untuk level pertama, di bawah 30 detik untuk level kedua, dan di bawah 40 detik untuk level ketiga
* **Konsep OOP**: menggunakan class `Achievement` yang di-inherit oleh class khusus untuk tiap jenis achievement.
* **Penerapan SOLID**:
  1. Single Responsibility Principle: Kelas achievement khusus hanya menangani satu achievement.
  2. Liskov Substitution Principle: Objek achievement dapat diganti oleh setiap class yang meng-inherit objek achievement tanpa mengubah logika program.
* **Design Pattern yang Digunakan**:
* **Code Snippet**:
```
public abstract class Achievement {
    public abstract boolean isAchieved(GameState state);
    public abstract String getAchievementName();
}

public class Champion extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        return state.isAllLevelsCompleted();
    }

    @Override
    public String getAchievementName() {
        return "Champion! Kamu menamatkan game ini, selamat!";
    }
}

public class Einstein extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        return state.getMistakes() == 0;
    }

    @Override
    public String getAchievementName() {
        return "Jenius seperti Einstein! Kamu tidak melakukan kesalahan sama sekali!";
    }
}

public class Turbo extends Achievement {
    @Override
    public boolean isAchieved(GameState state) {
        // Memeriksa apakah pemain memenuhi salah satu kondisi waktu untuk mendapatkan Turbo achievement
        return (state.getTimeElapsedLevel1() < 20) || 
               (state.getTimeElapsedLevel2() < 30) || 
               (state.getTimeElapsedLevel3() < 40);
    }

    @Override
    public String getAchievementName() {
        return "Turbo! Kamu sangat cepat!";
    }
}

```

## 4. Implementasi Fitur Lain

### 4.1 Fitur 1
* **Implementasi**:
* **Konsep OOP**:
* **Penerapan SOLID (Optional)**:
* **Design Pattern yang Digunakan (Optional)**:
* **Code Snippet**:
```
[Code snippet here]
```

### 4.2 Fitur 2
* **Implementasi**:
* **Konsep OOP**:
* **Penerapan SOLID (Optional)**:
* **Design Pattern yang Digunakan (Optional)**:
* **Code Snippet**:
```
[Code snippet here]
```

## 5. Screenshot dan Demo
* **Screenshot 1**: Turbo Achievement didapatkan jika menyelesaikan level sebelum waktu yang ditentukan
  ![Screenshot 2024-12-11 153734](https://github.com/user-attachments/assets/8ff987e8-d278-449b-8707-9a0013716b8b)
* **Screenshot 2**: Einstein  Achievement didapatkan jika menyelesaikan level dengan kesalahan >50
  ![Screenshot 2024-12-11 153742](https://github.com/user-attachments/assets/6087466a-c109-44ad-a0b3-baf552b1ce14)
* **Screenshot 3**: Champion  Achievement didapatkan jika telah menyelesaikan semua level (3 level)
  ![Screenshot 2024-12-11 154025](https://github.com/user-attachments/assets/4505bed1-cb07-476e-a7e0-274cbc3d4cd0)
* **Link Demo Video**: [URL]

## 6. Panduan Instalasi dan Menjalankan Game
1. [Langkah 1]
2. [Langkah 2]
3. [Langkah n]

## 7. Kendala dan Solusi
1. **Kendala 1**:
    * Solusi:
2. **Kendala 2**:
    * Solusi:

## 8. Kesimpulan dan Pembelajaran
* **Kesimpulan**:
* **Pembelajaran**:
