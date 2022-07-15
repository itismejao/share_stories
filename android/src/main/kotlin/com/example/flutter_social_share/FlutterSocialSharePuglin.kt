package br.com.etus.etus.flutter_social_share

import br.com.etus.etus.flutter_social_share.shareToStory.Instagram
import br.com.etus.etus.flutter_social_share.shareToStory.Facebook
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterSocialSharePlugin */
class FlutterSocialSharePlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var instagramStory: Instagram
    private lateinit var facebookStory: Facebook

    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        instagramStory = Instagram(flutterPluginBinding.applicationContext)
        facebookStory = Facebook(flutterPluginBinding.applicationContext)
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_social_share")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            "shareToStoryInstagram" -> {
                result.success(
                    instagramStory.share(
                        call.argument("backgroundAssetUri"),
                        call.argument("stickerAssetUri"),
                        call.argument("topColor"),
                        call.argument("bottomColor"),
                    )
                )
            }
            "shareToStoryFacebook" -> {
                result.success(
                        facebookStory.share(
                                call.argument("backgroundAssetUri"),
                                call.argument("stickerAssetUri"),
                                call.argument("topColor"),
                                call.argument("bottomColor"),
                        )
                )
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        instagramStory.activity = binding.activity
        facebookStory.activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
        instagramStory.activity = null
        facebookStory.activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        instagramStory.activity = binding.activity
        facebookStory.activity = binding.activity
    }

    override fun onDetachedFromActivity() {
        instagramStory.activity = null
        facebookStory.activity = null
    }
}
