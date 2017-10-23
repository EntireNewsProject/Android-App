# Entire News (Android News App)
## Read [Software Requirements Specification] (WiKi)

[![N|Solid](https://a.fsdn.com/sd/topics/android_64.png)](https://www.android.com/)

An app, that will show current and recent news from all over the world. A nice, clean, simple and interactive user interface that is easy to use. Will show user live news feed from various and trusted news sources (free API available at https://newsapi.org).

### Features including -

  - Multiple news sources in one app (i.e. BBC, CNN, ABCNews, USA Today, Times)
  - The ability to save and read news offline
  - The ability to read news that the user saved for reading offline.
  - Notify the user on a fixed interval for checking the news
  - The ability to share a news article with friends and family on social network

### Future plan (improvements) -

  - The ability to login and sync saved news articles across multiple devices
  - Search for news articles

Main goal: Design an app with current responsive user interface (with interactive animations), so that everyone can use it without the need to learn new stuff. As soon as the user opens the app, it will collect and display the user with fresh and trending news.

### Requirements -
- Language
    - Java / Kotlin & XML
    - Python
    - MongoDB Syntax
- IDE
    - Android Studio (version 3+ & Java JDK 8+)
    - PyCharm (with Python 3+)
    - WebStorm
    - Notepad++
- Server
    - Amazon AWS Elastic Compute Cloud (EC2)
    - Ubuntu 16.10


### Libraries Used

Free Libries used in this project:

* [Retrofit] - A type-safe HTTP client for Android and Java
* [Glide] - Glide is a fast and efficient image loading library for Android focused on smooth scrolling.
* [Calligraphy] - Custom fonts in Android.
* [Joda Time] - Joda-Time provides a quality replacement for the Java date and time classes.
* [Android About Page] - Create an awesome About Page for your Android App in 2 minutes
* [...] - Description

### Database Model

| Field Name | Data Type | Length | Example Data |
| ------ | ------ | ------ | ------ |
| _id | hash | 64 | 507f1f77bcf86cd799439011 |
| title | string | 1024 | Maria Expected to... |
| date | string | 32 | 2017-09-16T10:09:22Z |
| source | string | 1024 | http://www.bbc.com/news/uk-41292528 |
| cover | string | 1024 | https://ichef.bbci.co.uk/images/ic/1024x576/p05g9f0r.jpg |
| article | string | ∞ | Tropical Storm Maria will threaten portions... |
| summary | string | ∞ | Tropical Storm Maria is... |
| slug | string | 1024 | maria-expected-to... |
| saves | number |  | 654 |
| views | number |  | 4532 |
| published | boolean |  | true |
| deleted | boolean |  | false |
| hidden | boolean |  | false |
| keywords | boolean | 32 | `[ String ]` |
| tags | boolean | 32 | `[ String ]` |


License
----

2017 FSU License

**Made with love from Fresno, CA**  💗

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Software Requirements Specification]: <https://github.com/EntireNewsProject/Android-App/wiki>
   [Retrofit]: <http://angularjs.org>
   [Glide]: <https://github.com/bumptech/glide>
   [Calligraphy]: <https://github.com/chrisjenx/Calligraphy>
   [Joda Time]: <https://github.com/dlew/joda-time-android>
   [Android About Page]: <https://github.com/medyo/android-about-page>
   
