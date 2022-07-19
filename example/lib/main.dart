import 'package:flutter/material.dart';
import 'package:share_stories/share_stories.dart';

void main(){
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text("Exemplo"),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              ElevatedButton(
                  onPressed: (){
                    ShareStories.shareToInstagram(
                        backgroundAssetUri: file.uri,
                        //stickerAssetUri: image.uri,
                        topColor: Colors.blue,
                        bottomColor: Colors.red
                    );
                  },
                  child: Text("Story Instagram"))
            ],
          ),
        ),
      ),
    );
  }
}
