
**An Open-Source Password Locker**

**Project Synopsis**

Submitted in the partial fulfilment for the award of the degree of

**BACHELOR OF ENGINEERING**

**IN**

**Computer Science**

**Submitted By:**

**Team LUXX**

Lakshraj – 21BCS8099

Ankush Dua – 21BCS8106

Bhaskar Saini – 21BCS8113

**Under the Supervision of:**



**CHANDIGARH UNIVERSITY, GHARUAN, MOHALI - 140413 PUNJAB**

**March 2022**


**Table of Contents**

**Title Page										i**

**Table of Content									ii**

1. **Android Development Overview						1**

1.1 What is Android?								1

1.2 Android Application								2

1.3 Technology Used								3

1.4 Tools Used									4

1. **Project Overview**									**5**

2.1 Problem Definition								5

2.2 Project Overview								6

2.3 Planning & Tasks								7

2.4 Scope of the Project								8

2.5 Marketing and Income aspect						8

2.6 Improvements									9 

2.7 Definitions									10

`       `**3.	Bibliography									12**



**List of Figures**

|1|App Logo|
| :-: | :-: |
|2|Android Logo|
|3|Android Studio Logo|
|4|Figma Application Logo|
|5|SQLite Logo|
|6|Application Screens Demo Designs|








**1.1 What is Android?**

Android is an open source and Linux-based Operating System for mobile devices such as smartphones and tablet computers.

Android was developed by the Open Handset Alliance, led by Google, and other companies.

Android offers a unified approach to application development for mobile devices which means developers need only develop for Android, and their applications should be able to run on different devices powered by Android.

The first beta version of the Android Software Development Kit (SDK) was released by Google in 2007 where as the first commercial version, Android 1.0, was released in September 2008.

On June 27, 2012, at the Google 1/0 conference, Google announced the next Android version, 4.1 Jelly Bean. Jelly Bean is an incremental update, with the primary aim of improving the user interface, both in terms of functionality and performance.

The source code for Android is available under free and open-source software

licenses. Google publishes most of the code under the Apache License version 2.0 and the rest, Linux kernel changes, under the GNU General Public License version 2.










**1.2 Android Applications**

Android applications are usually developed in the Java language using the Android Software Development Kit. Once developed, Android applications can be packaged easily and sold out either through a store such as Google Play or the Amazon Appstore. Android powers hundreds of millions of mobile devices in more than 190

countries around the world. It's the largest installed base of any mobile platform and growing fast. Every day more than 1 million new Android devices are activated worldwide. This s tutorial has been written with an aim to teach you how to develop and package Android application. We will start from environment setup for Android

application programming and then drill down to look into various aspects of Android applications.


**1.3 Technology used**

**Front End**

**• Android software development kit**

The Android software development kit (SDK) includes a comprehensive set of

development tools. These include a debugger, libraries, a handset emulator based on QEMU, documentation, sample code, and tutorials. Currently supported development platforms include computers running Linux, Mac OS X 10.5.8 or later, and Windows XP or later. As of March 2015, the SDK is not available on Android itself, but the software development is possible by using specialized Android applications.




Android Studio, [9] made by Google and powered by IntelliJ, is the official IDE;

however, developers are free to use others. Additionally, developers may use any text editor to edit Java and XML files, then use command line tools ( Java 

Development Kit and Apache Ant are required) to create, build and debug Android applications as well as control attached Android devices (e.g., triggering a reboot, installing software package(s) remotely).

**• Java development kit**

The Android build process depends on a number of tools from the JDK. Check out the build system overview documentation. The first big piece we need from JDK is javac- all your source code written in Java needs to be compiled before it can be converted to the DEX format.

Once your code has been compiled, dexed, and packaged into an APK, we need jar signer to sign the APK.

There are some efforts out there to bring Java 8 features to Android, most notably Gradle-retro lambda. Some of these require JDK 8 to compile properly.


**1.4 Tools used for project**



` `**• Android Studio**

Android Studio is the official Integrated development environment (IDE) for Android platform development.

It was announced on May 16, 2013 at the Google /O conference. Android Studio is freely available under the Apache License 2.0.

Android Studio was in early access preview stage starting from version 0.1 in May 2013, then entered beta stage starting from version 0.8 which was released in June 2014.




The first stable build was released in December 2014, starting from version 1.0. Based on JetBrains' IntelliJ IDEA software, Android Studio is designed specifically for Android development. It is available for download on Windows , Mac OS X and Linux,

and replaced Eclipse Android Development Tools (ADT) as Google's primary IDE for native Android application development.


**Android Tool 1: Figma**

Figma is a vector graphics editor and prototyping tool which is primarily web-based, with additional offline features enabled by desktop applications for macOS and Windows. The Figma mobile app for Android and iOS allow viewing and interacting with Figma prototypes in real-time on mobile devices.

**Android Tool 2: The SDK and AVD Manager**

This tool serves a number of important functions. It manages the different versions of the Android SDKS (build targets) that you can develop for, as well as third-party add- on,  tools, devices drivers, and documentation. Its second function is to manage the Android Virtual Device configurations (AVDS) you use to configure emulator instances.

**Android Tool 3: Android Debug Bridge**

The Android Debug Bridge (ADB) connects other tools with the emulator and devices. Besides being critical for the other tools (most especially the Eclipse ADT plug-in) to function, you can use it yourself from the command line to upload and download files, install and uninstall packages, and access many other features via the shell on the device or emulator.





**2. Project Overview**

**2.1 Problem Definition**

In This Modern Era, Due to development in the Internet, Billions of people connects With each Other on the Daily Basis, and Do Billions of Transactions in a Day, Post Billions of Photos, Stories, Tweets in a Day. And all these accessibilities are Available to everyone By Simply Entering Their Login Details.

**Now I am Going to Discuss some Cases to Understand the Problem:**

Case 1.  A User is Using an Easy Password. 

Result - Easily Crack able or Guessable by Anyone – **NOT Safe.**

Case 2. A User is Using a Same Strong Complex Password on All           Sites/Apps.

Result – If any of the one site/app get Hacked or anything like Data Breach Happened. All of your sites having same password will get in Risk. – **NOT Safe.**

Case 3. A User is Using Complex and Different Password on Every Site/app.

Result – Got Secured from Cyber Security View but Due to Many Different passwords. User is unable to Remember all Password. – **SAFE but HARD to Remember Passwords.**

Case 4. User Used third party Service to Save Password like Google Password and NORD Pass etc.

Result – Service like these is on Constant Target of hacker and If any Network Disaster (Service Down, Data Breached, Servers Off) happen you won’t be able to Login without Passwords. - **Partially Safe**  


Case 5. User is using Physically / Hard Copy of the Passwords to remember.

Result – Any written physical thing can be got Lost, Can Be Damaged, can be destroyed Completely. -**Not Safe.**




**Now From All the Above Cases we Understand the Problems Which are:** 

- **To not use an Easy Password.**
- **To not use Same Password in all Site.**
- **To can’t to Able to remember Password due to complexity**
- **To not Completely relay on third party Services like Google passwords.**
- **To not Store Password in Hard Copy Form.**

**Therefore, To Counter All the above Cases we are going to Develop an application. - Lockward**



2. **Project Overview**

**Lockward** – In Short of Lock you Passwords. 

Our Goal is Simple which is to overcome the Disadvantages of the Above-mentioned Cases.

When You launch Our App, you will be redirected to the set Password and Set **Encryption Key** page. There User Will be able to Set a Strong Password and The Custom Key.

After that User Will be able to enter the Login detains and it will be encrypted by seconds. And User will be able to Retrieve the password with the help of his Fingerprint Scanner and PIN.

And with aspects like No Internet Connectivity these is no chance of any background function to send your password or Data to somewhere on the internet.

We will have an Option of Export and Import. With Export option all password saved in app will be exerted into a file for backup purpose in case of if someone lost or damage the phone. Later on, your new Phone you can import that file and all your password will be saved 




again in the app. Exported file will be encrypted so even someone stole it. the file will not be of any use of them. 

**Key Features :**

- No Internet Connectivity
- Open-Source Code
- Best Encryption Algorithms as Per Standards
- Custom Encryption Key
- Backup and Restore Option
- Free to Use

2. **Planning and Tasks:**

We are going to use **Android Studio** as our main IDE to develop the application. Main language for the Backend processes we will be using **KOTLIN** and **XML** as our designing layout and for XML Values etc.





For UI/UX designing we are going to use **Figma**. Then design from figma will be implemented to the app through XML formatted Code.

For Saving Encrypted Password and other offline data. we will be using **SQLite** as we can run all SQL related Actions on it later.









2. **Scope of the Project:**

Firstly, I want to share a Realtime experience happened with me. As a core internet user, I also have too many logins and I have also saved mostly of my passwords on google passwords but one day Google’s servers were down and I was unable to login in many site.

Outcome – What we want is, a Password saver which is safe and also Works without any servers. Which our App will do.

For All the Users who find problem in Remembering the Password and won’t trust third party Services like NORD pass and Google Passwords.

In India there are 70 Crore internet users and if in case every internet user has 10 accounts on average. it means these are 700 Crore plus account details to be saved.

For all these people we want to develop an application from which they can safely saves our passwords. 


2. **Marketing And Income Aspect:**

As every Developer and Application company they know that if their project or project is an open source then we can’t charge for it. as the code of the product/project is available for public use. So, the best way to gain some profit is from the product is Through Donations.

2. **Improvement:**

The Scope for Improvement is Unlimited for our Project because of being an open-source project anyone can contribute in our project from Github Commits.







2. **Definitions :**

**Encryption :** Encryption is a means of securing digital data using one or more mathematical techniques, along with a password or "key" used to decrypt the information. The encryption process translates information using an algorithm that makes the original information unreadable.

**Android Studio :** Android Studio is the official integrated development environment for Google's Android operating system, built on JetBrains' IntelliJ IDEA software and designed specifically for Android development.

**KOTLIN :** Kotlin is a cross-platform, statically typed, general-purpose programming language with type inference. Kotlin is designed to interoperate fully with Java, and the JVM version of Kotlin's standard library depends on the Java Class Library, but type inference allows its syntax to be more concise.

**Figma :** Figma is a vector graphics editor and prototyping tool which is primarily web-based, with additional offline features enabled by desktop applications for macOS and Windows. The Figma mobile app for Android and iOS allow viewing and interacting with Figma prototypes in real-time on mobile devices.













1. **Bibliography**

- <https://developer.android.com/docs>
- <https://en.wikipedia.org/>
- <https://developer.android.com/kotlin>
- <https://developer.android.com/about>



**Demo Screens Ideas** (Extras for Showing Design Idea, Can Be changed Later As per Requirements of the Project.) :




***Project Synopsis By: Team Luxx***
