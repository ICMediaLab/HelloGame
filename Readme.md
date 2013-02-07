HelloGame is ICMediaLab's first group game development.

---

Setting Up LWJGL and Slick in Eclipse:

1. Launch Eclipse and create a new Java project
2. Right-click your project's name and select "properties"
3. Select 'Java Build Path' and then the 'Libraries' tab.
4. Click "Add External Jars.." and select the following files:
	lwjgl.jar
	Slick.jar
	jinput.jar
	lwjgl_util.jar (if want to use OpenGL's GLU class)

5. Still in the 'Libraries' tab, click in the arrow next to lwjgl.jar
6. Double click 'Native library location'
7. Select folder that contain the natives (that were downloaded and extracted as instructed above)
8. Choose the folder of your Operating System
	natives\windows folder which contains the *.dll files,
	natives/macosx which contains the *.dylib and *.jnilib
	natives/linux which contains the *.so