# 1. Pregătirea proiectului

Asigură-te că ai în același folder fișierele:

* **Server.java**
* **Client.java**

# 2. Compilarea programelor

Deschide un terminal în folderul proiectului și rulează comanda:
**javac Server.java Client.java**

# 3. Pornirea serverului

Rulează:
**java Server**

Mesaj așteptat:

* *Server pornit pe portul 5000*
* *Scrie 'shutdown' pentru a opri serverul.*

# 4. Pornirea clientului

Deschide un al doilea terminal și rulează:
**java Client**

Mesaj afișat:

* *Conectat la server.*
* *Trimite cel puțin 3 rânduri. Scrie 'exit' pentru a închide conexiunea.*

# 5. Trimiterea datelor către server

Clientul va afișa:
*Scrie un rând (sau 'exit'):*

După trimiterea a cel puțin 3 rânduri, serverul va răspunde cu textul primit + 2 rânduri noi.

# 6. Închiderea clientului

Scrie:
**exit**

# 7. Închiderea serverului manual

În consola serverului tastează:
**shutdown**

Serverul va închide toate conexiunile active și se va opri.

# 8. Testarea multi-threading-ului

* Pornește serverul.
* Deschide 2–3 terminale noi.
* Rulează **java Client** în fiecare.
* Trimite mesaje din fiecare client.
* Observă că serverul gestionează fiecare client separat, în thread diferit.
