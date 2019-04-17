# Android-Webi-Image-Loader
Android library #1 on GitHub. WIL aims is used for asynchronous remote image loading, caching and displaying. The purpose of the WIL is to abstract the downloading (images, pdf, zip, etc) and caching of remote resources (images, JSON, XML, etc) so that client code can easily "swap" a URL for any kind of files ( JSON, XML, etc) without worrying about any of the details. Resources which are reused often are continually re-downloaded and cached, WIL doesn't use infinite memory.


![imageeditlsajdf (1)](https://user-images.githubusercontent.com/48721096/56308468-0c1cc200-6150-11e9-96f2-d924fcf3b200.png)


# Features:

* Images and JSON are cached efficiently (in-memory only, no need for caching to disk);
* Asynchronously download the images for a url
* The cache have a configurable max capacity and evict images not recently used;
* The library is easy to integrate into new Android project / apps;

# Installation

Up to now, the library is only available in JitPack. Please add this code to your build.gradle file on project level:

        allprojects {
          repositories {
            ...
            maven { url 'https://jitpack.io' }
          }
        }
        
# How do I use Glide?

        // For a simple view:
        @Override public void onCreate(Bundle savedInstanceState) {
          ...
          ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

          WebiImageLoader.with(this).load("http://goo.gl/gEgYUd").into(imageView);
        }

        // For a simple image list:
        @Override public View getView(int position, View recycled, ViewGroup container) {
          final ImageView myImageView;
          if (recycled == null) {
            myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
          } else {
            myImageView = (ImageView) recycled;
          }

          String url = myUrls.get(position);

          WebiImageLoader
                        .load(url)
                        .placeHolder(R.drawable.loading_spinner)
                        .into(myImageView);

          return myImageView;
        }
        
        
# Compatibility

* Minimum Android SDK: Glide v4 requires a minimum API level of 14.
* Compile Android SDK: Glide v4 requires you to compile against API 26 or later

# Thanks
Thank you for using the WIL!

© 2019-2020 (Designed and developed by John Webi)
