# 项目介绍
    因为项目需要加载超出屏幕的图片，类似新浪微薄长图，所以结合Github上的[Subsampling Scale Image View](https://github.com/davemorrissey/subsampling-scale-image-view)
    和Fresco图片加载框架一起实现该效果。后续会添加Glide等。
    
#使用说明
    方式一：
        拷贝longimage中的文件到你自定义路径下，然后自定义Application，设置Fresco的初始化，在xml中使用
        SubsamplingScaleImageView,在Activity中获取该控件，调用setImageUri方法传入uri地址即可。
        ![拷贝文件](./image/file.png)
        ![自定义Application](./image/longApplication.png)
        ![xml中使用](./image/long_xml.png)
        ![activity中设置地址](./image/long_activity.png)

        
#注意事项
    该项目中使用了Fresco等图片加载框架，所以如果需要混淆，请自行填加。
 
#更新日志
     1.0.0
       结合[Subsampling Scale Image View](https://github.com/davemorrissey/subsampling-scale-image-view)
       和Fresco 实现长图的加载


#联系方式
   Email:markruo92@gmail.com  