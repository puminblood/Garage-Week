void setup() {
  Serial.begin(9600);
  pinMode(8, OUTPUT);
}

void loop() {
  if (Serial.available() > 0) {
    String received = Serial.readString(); // Lit une chaîne de caractères
    Serial.print("Reçu: "); // Debug: affiche ce qui est reçu
    Serial.println(received);
    if (received == "1") {
      digitalWrite(8, HIGH);
      Serial.println("Pin 8 activée.");
    } else {
      digitalWrite(8, LOW);
      Serial.println("Pin 8 désactivée.");
    }
  }
}
