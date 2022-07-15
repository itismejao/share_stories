import 'dart:async';
import 'dart:io';

import 'package:flutter/painting.dart';
import 'package:flutter/services.dart';

class FlutterSocialShare {
  static const MethodChannel _channel =
      const MethodChannel('flutter_social_share');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> shareToInstagram({
    Uri? backgroundAssetUri,
    Uri? stickerAssetUri,
    Color? topColor,
    Color? bottomColor,
  }) async {
    assert(backgroundAssetUri != null || stickerAssetUri != null);

    final Map<String, String> params = Map();

    if (backgroundAssetUri != null) {
      params["backgroundAssetUri"] = Platform.isAndroid
          ? backgroundAssetUri.toString()
          : backgroundAssetUri.path;
    }
    if (stickerAssetUri != null) {
      params["stickerAssetUri"] = Platform.isAndroid
          ? stickerAssetUri.toString()
          : stickerAssetUri.path;
    }
    if (topColor != null) {
      params["topColor"] = "#${topColor.value.toRadixString(16)}";
    }
    if (bottomColor != null) {
      params["bottomColor"] = "#${bottomColor.value.toRadixString(16)}";
    }

    return await _channel.invokeMethod('shareToStoryInstagram', params);
  }

  static Future<String?> shareToFacebook({
    Uri? backgroundAssetUri,
    Uri? stickerAssetUri,
    Color? topColor,
    Color? bottomColor,
  }) async {
    assert(backgroundAssetUri != null || stickerAssetUri != null);

    final Map<String, String> params = Map();

    if (backgroundAssetUri != null) {
      params["backgroundAssetUri"] = Platform.isAndroid
          ? backgroundAssetUri.toString()
          : backgroundAssetUri.path;
    }
    if (stickerAssetUri != null) {
      params["stickerAssetUri"] = Platform.isAndroid
          ? stickerAssetUri.toString()
          : stickerAssetUri.path;
    }
    if (topColor != null) {
      params["topColor"] = "#${topColor.value.toRadixString(16)}";
    }
    if (bottomColor != null) {
      params["bottomColor"] = "#${bottomColor.value.toRadixString(16)}";
    }

    return await _channel.invokeMethod('shareToStoryFacebook', params);
  }


}
