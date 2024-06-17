#include <SPI.h>
#include <SD.h>

File myFile;

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // Attend que la connexion série soit établie
  }
  
  Serial.println("Initializing SD card...");
  if (!SD.begin(4)) {
    Serial.println("Initialization failed!");
    return;
  }
  Serial.println("Initialization done.");

  myFile = SD.open("test.wav");
  if (myFile) {
    while (myFile.available()) {
      Serial.write(myFile.read());
    }
    myFile.close();
    Serial.println("<EOF>");  // Envoyer un marqueur de fin de fichier
  } else {
    Serial.println("Error opening test.wav");
  }
}

void loop() {
  // rien à faire ici
}
