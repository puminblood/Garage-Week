#include <SoftwareSerial.h>
#include <Servo.h>

SoftwareSerial myBluetooth(2, 3); // RX, TX
const int greenLedPin = 8;  // LED verte
const int orangeLedPin = 6; // LED orange
const int redLedPin = 7;    // LED rouge
Servo myServo;  // Créer un objet servo pour contrôler le servomoteur

void setup() {
  pinMode(greenLedPin, OUTPUT);
  pinMode(orangeLedPin, OUTPUT);
  pinMode(redLedPin, OUTPUT);
  myServo.attach(9); // Attacher le servomoteur au pin 9
  Serial.begin(9600);
  myBluetooth.begin(9600);

  Serial.println("Prêt à recevoir des données...");
}

void loop() {
  if (myBluetooth.available()) {
    char received = myBluetooth.read();
    Serial.print("Reçu : ");
    Serial.println(received);

    switch (received) {
      case '0':
        digitalWrite(greenLedPin, HIGH); // Allume la LED verte pendant 3 secondes
        operateServo(); // Faire fonctionner le servomoteur
        delay(3000);
        digitalWrite(greenLedPin, LOW);
        break;
        
      case '1':
        // Faire clignoter la LED orange 3 fois avec le mouvement du servomoteur
        for (int i = 0; i < 3; i++) {
          digitalWrite(orangeLedPin, HIGH);
          myServo.write(0);
          delay(250);
          digitalWrite(orangeLedPin, LOW);
          myServo.write(180);
          delay(250);
        }
        digitalWrite(orangeLedPin, HIGH);
        delay(250);
        digitalWrite(orangeLedPin, LOW);
        break;
        
      case '2':
        digitalWrite(redLedPin, HIGH); // Allume la LED rouge (le servomoteur ne bouge pas)
        delay(3000);
        digitalWrite(redLedPin, LOW);
        break;
        
      default:
        // Éteint toutes les LEDs si aucune condition n'est remplie
        digitalWrite(greenLedPin, LOW);
        digitalWrite(orangeLedPin, LOW);
        digitalWrite(redLedPin, LOW);
        break;
    }
  }
}

void operateServo() {
  for (int i = 0; i < 3; i++) { // Fonction pour faire tourner le servomoteur
    myServo.write(0);
    delay(500);
    myServo.write(180);
    delay(500);
  }
}
