# FileTranslator using Google Script in Java

This project uses Google Script App to read through string into a file and do a search replace for target text into another language.


----------
You can use google script which has FREE translate API. All you need is a common google account and do these THREE EASY STEPS.

### 1) Create Google Script
Create new script with below code on google script:
```
var mock = {
  parameter:{
    q:'hello',
    source:'en',
    target:'ja'
  }
};

function doGet(e) {
  e = e || mock;
  var sourceText = ''
  if (e.parameter.q){
    sourceText = e.parameter.q;
  }
  var sourceLang = 'auto';
  if (e.parameter.source){
    sourceLang = e.parameter.source;
  }
  var targetLang = 'en';
  if (e.parameter.target){
    targetLang = e.parameter.target;
  }
  var translatedText = LanguageApp.translate(sourceText, sourceLang, targetLang, {contentType: 'html'});
  return ContentService.createTextOutput(translatedText).setMimeType(ContentService.MimeType.JSON);
}
```

### 2) Publish
-> Click Publish 
-> Deploy as webapp 
-> Select this option for Who has access to the app : "Anyone even anonymous"
-> Deploy and then copy your web app url, you will need it for calling translate API. 

[![N|Solid](https://i.stack.imgur.com/VANXa.png)]()

### 3) Call code from Java Class
- Copy Translate.java into your C drive(on windows) with sample-input.txt and sample-output.txt
- Change the SCRIPT URL in Translate.java
- Change content of sample-input.txt (if you want)
- Compile java code using "javac Translate.java" and Run it using "java Translate".



License
----
GNU General Public License v3.0
