### [OneHop](https://github.com/warren-bank/Android-MobileMesh/tree/proof-of-concept/one-hop/main)

Mobile app for Android to prove the viability of the design strategy for [MobileMesh](https://github.com/warren-bank/Android-MobileMesh) as described by its [whitepaper](https://github.com/warren-bank/Android-MobileMesh/blob/master/etc/docs/whitepaper.txt).

#### Proof of Concept

* uses Bluetooth LE to advertise and discover nearby devices
* uses Bluetooth Classic to open a socket server on each device and pass data between adjacent devices
  - the goal of _MobileMesh_ is to allow data to travel multiple hops through an interconnected network
  - the goal of _OneHop_ is to test that data can travel exactly one hop

#### Legal

* copyright: [Warren Bank](https://github.com/warren-bank)
* license: [GPL-2.0](https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt)
