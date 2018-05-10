Názov:	DBS projekt  
Autori: Karolína Giertlová , Marek Krátky  
Dátum:	Marec 25, 2018  

# Analytický report

## Generovanie dát do tabuliek

V našom projekte sme do tabuliek generovali dáta nasledovnými dvoma spôsobmi:  
1) pri malých veľkostiach tabuliek (číselníky) sme staticky vložili vopred 
pripravené hodnoty  
2) pri ostatných tabuľkách sme generovali dáta použitím rôznych 
funkcií knižnice **Faker** (*name, date, address, company*), ako aj
využitím knižnice **Random** na vygenerovanie náhodných čísel, dátumov a
písmen.  
  
Pri generovaní dát sme dbali na to, aby bol význam 
vygenerovaných dát konzistentný s významom atribútu entity, do ktorej
boli dáta generované.

## Analýza dát konkrétnych tabuliek
V tejto časti reportu sa nachádza podrobný opis a analýza dát 
vložených do jednotlivých tabuliek.  
Každá tabuľka obsahuje atribút *ID*, ktorý slúži ako primárny kľúč a jeho
typ sme nastavili ako *serial*, lebo nám bola pri generovaní dát
užitočná jeho autoinkremenálna vlastnosť.
### lekár
- dáta boli generované nasledovne:
1) meno - faker.name().fullName()
2) prax_roky - random integer od 2 do 50
- počet riadkov: 1200 030
```
SELECT COUNT(ID) FROM lekar;
```
- numerické atribúty (prax_roky):  

min: 2

```
SELECT MIN(prax_roky) FROM lekar;
```
max: 51
```
SELECT MAX(prax_roky) FROM lekar;
```
avg: 26,49
```
SELECT AVG(prax_roky) FROM lekar;
```
medián: 27
```
SELECT percentile_disc(.50) 
WITHIN GROUP (ORDER BY prax_roky) FROM lekar;
```
dolný kvartil: 14
```
SELECT percentile_disc(.25) 
WITHIN GROUP (ORDER BY prax_roky) FROM lekar;
```
horný kvartil: 39
```
SELECT percentile_disc(.75) 
WITHIN GROUP (ORDER BY prax_roky) FROM lekar;
```
- nominálne atribúty (meno):
počet rôznych mien je 1 007 802
```
SELECT COUNT(DISTINCT meno) FROM lekar;
```

### liečivý účinok
- dáta boli generované nasledovne:
1) ucinok - staticky zadané
2) doba_liecby_dni - staticky zadané
- počet riadkov: 20 

- numerické atribúty (doba_liecby_dni):  

min: 2

max: 30

avg: 13,3

medián: 11

dolný kvartil: 6

horný kvartil: 18

- nominálne atribúty (účinok):
počet rôznych účinkov je rovnaký, ako počet riadkov - každý 
účinok je unikátny

### liek
- dáta boli generované nasledovne:
1) nazov - random znak od A po Z dva krát po sebe + pomlčka + 
random integer od 100 po 999
2) cena - random integer od 2 po 50
3) hmotnost_gram - random integer od 1 po 20
4) datumExspiracie - faker.date().between(startDate, endDate);
- počet riadkov: 20 000 010 

- numerické atribúty (cena):  

min: 2

max: 50

avg: 24,999

medián: 25

dolný kvartil: 12,49

horný kvartil: 37,5

- numerické atribúty (hmotnost_gram):  

min: 1

max: 20

avg: 9,9988

medián: 10

dolný kvartil: 5

horný kvartil: 15

- numerické atribúty (datumExspiracie):  

min: 2018-03-30

max: 2019-03-29

avg: -

medián: 2018-09-28

dolný kvartil: 2018-06-29

horný kvartil: 2018-12-28

- nominálne atribúty (nazov):
počet rôznych názvov je 562 500

### liek_liečivý účinok
- dáta boli generované nasledovne:
1) ID_liek - random integer od 1 po počet liekov
2) ID_liecivyUcinok - random integer od 1 po počet liečivých účinkov
- počet riadkov: 1 600 305

- numerické atribúty (ID_liek):  

min: 12

max: 19 999 954

avg: 9 997 948,562

medián: 9 990 550

dolný kvartil: 5 001 335

horný kvartil: 14 992 692

- numerické atribúty (ID_liecivyUcinok):  

min: 1

max: 19

avg: 10,0025

medián: 10

dolný kvartil: 5

horný kvartil: 15

### liek_vedľajší účinok
- dáta boli generované nasledovne:
1) ID_liek - random integer od 1 po počet liekov
2) ID_vedlajsiUcinok - random integer od 1 po počet vedľajších účinkov

- počet riadkov: 1 100 005

- numerické atribúty (ID_liek):  

min: 34

max: 19 999 995

avg: 9 997 814,347

medián: 9 997 754

dolný kvartil: 5 003 697

horný kvartil: 14 990 324

- numerické atribúty (ID_vedlajsiUcinok):  

min: 1

max: 19

avg: 9,99981

medián: 10

dolný kvartil: 5

horný kvartil: 15

### liek_objednávka
- dáta boli generované nasledovne:
1) ID_liek - random integer od 1 po počet liekov
2) ID_objednavka - random integer od 1 po počet objednávok

- počet riadkov: 1 300 001

- numerické atribúty (ID_liek):  

min: 2

max: 19 999 979

avg: 9 999 744,214

medián: 10 003 447

dolný kvartil: 4 985 771

horný kvartil: 14 997 418

- numerické atribúty (ID_objednavka):  

min: 2

max: 1 399 999

avg: 700 432,0939

medián: 700 347

dolný kvartil: 350 568

horný kvartil: 1 050 214

### objednávka
- dáta boli generované nasledovne:
1) ID_predajna - random integer od 1 po počet predajní
2) faktura - random integer od 50 po 200
3) sposobPlatby - random výber z dvoch možností - karta alebo hotovosť


- počet riadkov: 1 400 010

- numerické atribúty (faktura):  

min: 50

max: 200

avg: 125,0028

medián: 124,98

dolný kvartil: 87,5

horný kvartil: 162,54

- numerické atribúty (ID_predajna):  

min: 1

max: 1 999 998

avg: 1 000 267,518

medián: 1 000 210

dolný kvartil: 500 663

horný kvartil: 1 500 252

- nominálne atribúty (sposobPlatby):
počet platieb kartou: 699 987
počet platieb hotovosťou: 700 023

### predajňa

- dáta boli generované nasledovne:
1) nazov - faker.company().name()
2) adresa - faker.address().streetAddress()

- počet riadkov: 2 000 005

- nominálne atribúty (názov):
počet rôznych názvov predajní: 2844

- nominálne atribúty (adresa):
počet rôznych adries predajní: 1999149

### predajňa_zamestnanec
- dáta boli generované nasledovne:
1) id_predajna - random integer od 1 po počet predajní
2) id_zamestnanec - random integer od 1 po počet zamestnancov

- počet riadkov: 1 700 005 

- numerické atribúty (ID_predajna):  

min: 3

max: 1 999 999

avg: 1 000 693.054

medián: 1 000 709

dolný kvartil: 501022

horný kvartil: 1 500 707

- numerické atribúty (ID_zamestnanec):  

min: 2

max: 1 799 995

avg:  900 247.591

medián: 900 532

dolný kvartil: 450413

horný kvartil: 1 350 200

### predpis
- dáta boli generované nasledovne:
1) id_lekar - random integer od 1 po počet lekarov
2) id_objednavka - random integer od 1 po počet objednavok
3) datumExspiracie - datumExspiracie - faker.date().between(startDate, endDate);

- počet riadkov: 2 000 001 

- numerické atribúty (ID_lekar):  

min: 1

max: 1 199 999

avg:  599 824.736

medián:  600 058

dolný kvartil: 299 837

horný kvartil:  899 291

- numerické atribúty (ID_objednavka):  

min: 1

max: 1 399 999

avg:   699 782.128

medián:  699 636

dolný kvartil:  349909

horný kvartil:  1 049 668

- numerické atribúty (datumExspiracie):  

min: 2018-03-30

max: 2018-04-14

avg: -

medián: 2018-04-07

dolný kvartil: 2018-04-02

horný kvartil: 2018-04-11

### vedlajsiUcinok
- dáta boli generované nasledovne:
1) ucinok - staticky zadané
2) pravdepodobnost - staticky zadané
3) jeSkodlivy - staticky zadané

- počet riadkov: 20 

- numerické atribúty (pravdepodobnost):  

min: 0.01

max: 0.36

avg: 0.1385

medián: 0.08

dolný kvartil: 0.04

horný kvartil: 0.19

- nominálne atribúty (účinok):
počet rôznych účinkov je rovnaký, ako počet riadkov - každý 
účinok je unikátny

- nominálne atribúty (jeSkodlivy):
počet slov ano (škodlivých): 8
počet slov nie (neškodlivých): 12 

### zamestnanec

- dáta boli generované nasledovne:
1) meno - faker.name().fullName();
2) prax_roky - random integer od 2 do 50

- počet riadkov: 1 800 000

- numerické atribúty (prax_roky):  

min: 2

max: 51

avg:   26.49

medián:  26

dolný kvartil:  14

horný kvartil:  39

- nominálne atribúty (meno):
počet rôznych mien je 1 401 854