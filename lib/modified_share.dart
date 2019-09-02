import 'dart:async';

import 'package:flutter/services.dart';

class ModifiedShare {
  static const MethodChannel _channel = const MethodChannel('modified_share');

  static Future<String> share(Map<String, String> shareDetails) async {
    shareDetails["onlyWhatsApp"] = 'false';

    String grantedWritePermission =
        await _channel.invokeMethod('checkWriteStoragePermission');
    if (grantedWritePermission == 'true') {
      final String response =
          await _channel.invokeMethod('share', shareDetails);
      return response;
    } else {
      final String msgToShow = 'Please allow access to Share';
      await _channel.invokeMethod('showToast', {
        "msgToShow": msgToShow,
      });
      return '';
    }
  }

  static Future<String> shareViaWhatsApp(
      Map<String, String> shareDetails) async {
    shareDetails["onlyWhatsApp"] = 'true';

    String grantedWritePermission =
        await _channel.invokeMethod('checkWriteStoragePermission');
    if (grantedWritePermission == 'true') {
      final String response =
          await _channel.invokeMethod('share', shareDetails);
      return response;
    } else {
      final String msgToShow = 'Please allow access to Share via WhatsApp';
      await _channel.invokeMethod('showToast', {
        "msgToShow": msgToShow,
      });
      return '';
    }
  }
}
