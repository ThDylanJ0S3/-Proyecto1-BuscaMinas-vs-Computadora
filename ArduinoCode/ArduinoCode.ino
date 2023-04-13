
int btnLEFT = 9;
int btnDOWN = 8;
int btnRIGHT = 10;
int btnUP = 11;
int btnClick = 12;

int buzzerActivo = 5;
int ledAzul = 4;

void setup(){

  Serial.begin(9600);

  pinMode(btnLEFT, INPUT);
  pinMode(btnDOWN, INPUT);
  pinMode(btnRIGHT, INPUT);
  pinMode(btnUP, INPUT);
  pinMode(btnClick, INPUT);

  pinMode(buzzerActivo,OUTPUT);
  pinMode(ledAzul,OUTPUT);
}

void loop() {

  if (Serial.available() > 0) { // Si hay datos en el puerto serial
    char data = Serial.read(); // Leemos los datos
    if (data == 'B') { // Si la señal recibida es 'B', activa el buzzer
      digitalWrite(buzzerActivo, HIGH);
      delay(1000);
      digitalWrite(buzzerActivo, LOW);
    } else if (data == 'L') { // Si la señal recibida es 'L', activa el led
      digitalWrite(ledAzul, HIGH);
      delay(1000);
      digitalWrite(ledAzul, LOW);
    }else if(data =='M'){
      digitalWrite(buzzerActivo, HIGH);
      delay(250);
      digitalWrite(buzzerActivo, LOW);
      delay(250);
      digitalWrite(buzzerActivo, HIGH);
      delay(250);
      digitalWrite(buzzerActivo, LOW);
      }
  }
  if(digitalRead(btnLEFT) == LOW) {
    delay(100);
    Serial.println("BTN_LEFT");
    delay(1000);
  }
  
  if(digitalRead(btnDOWN) == LOW) {
    delay(100);
    Serial.println("BTN_DOWN");
    delay(1000);
  }
  
  if(digitalRead(btnRIGHT) == LOW) {
    delay(100);
    Serial.println("BTN_RIGHT");
    delay(1000);
  }
  
  if(digitalRead(btnUP) == LOW) {
    delay(100);
    Serial.println("BTN_UP");
    delay(1000);
  }
  
  if(digitalRead(btnClick) == LOW) {
    delay(100);
    Serial.println("BTN_CLICK");
    delay(1000);
  }
}
