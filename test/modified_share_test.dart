import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:modified_share/modified_share.dart';

void main() {
  const MethodChannel channel = MethodChannel('modified_share');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await ModifiedShare.share({}), '42');
  });
}
