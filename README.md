# modified_share



[![pub package](https://img.shields.io/badge/pub-0.0.1-green.svg)](https://pub.dartlang.org/packages/modified_share)



A Flutter plugin for ANDROID that allows sharing text, images via popular Social Networks such as Facebook, WhatsApp, Instagram etc. 


## Installing

```yaml

dependencies:
    modified_share: ^0.0.1

```


### Import

```dart

import 'package:modified_share/modified_share.dart';

```


## How To Use

```

String imageURL = 'https://movieassetsnew.komparify.com/original/9e07b90cdca0e71d8ab6d23c997cba99024018ad', 
  content = "Petta\n\nMore details here at Flixjini: https://in.flixjini.com/movie/petta?search_keyword=Petta \n\nCheck out Flixjini App at: https://in.flixjini.com";
    Map<String, String> shareMessageDetails = {
      "imageURL": imageURL,
      "content": content,
    };

    // for sharing via WhatsApp 
      await ModifiedShare.shareViaWhatsApp(shareMessageDetails);
    // for sharing 
      await ModifiedShare.share(shareMessageDetails);


```


## Bugs & Requests

If you encounter any bugs feel free to open an issue. Raise a ticket on github for suggestions. Pull request are also welcome. 

I am happy to receive suggestions and feedback at my email address: smmziaul@gmail.com



### Flutter

For help getting started with Flutter, view our online [documentation](https://flutter.io/).

For help on editing plugin code, view the [documentation](https://flutter.io/platform-plugins/#edit-code).



## License

Copyright 2019 Soofi Hussain S M M 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.