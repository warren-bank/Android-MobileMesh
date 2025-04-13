### [MobileMesh](https://github.com/warren-bank/Android-MobileMesh)

Mobile app for Android that uses Bluetooth to create an ad-hoc multi-hop mesh network of interconnected devices.

Accounts are generated on device.<br/>
Contacts are account details shared by other users through a handshake protocol.<br/>
Messages between contacts use end-to-end encryption.

Includes support for external apps to send arbitrary messages through the network to contacts.<br/>
Minimal implementations of external apps for common use cases (ex: chat, voip) will be added over time.

#### Design Document

* [whitepaper](./etc/docs/whitepaper.txt)
  - initial notes
    * design goals
    * survey of existing projects w/ pros vs. cons
    * deeper dive into existing projects w/ code that can be leveraged
    * research into relevant APIs and libraries
    * architectural strategies and considerations
      - expressed mostly in a Java-like pseudocode
  - pre-dates any actual coding
    * readers should always refer to the versioned source code for nuance

#### Legal

* copyright: [Warren Bank](https://github.com/warren-bank)
* license: [GPL-2.0](https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt)
