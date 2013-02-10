## HelloGame is ICMediaLab's first group game development.

#### Setting Up LWJGL and Slick in Eclipse:
<ol>
<li>Launch Eclipse and create a new Java project</li>
<li>Right-click your project's name and select "properties"</li>
<li>Select 'Java Build Path' and then the 'Libraries' tab.</li>
<li>Click "Add External Jars.." and select the following files:<br /><ul>
<li>- lwjgl.jar<br /></li>
<li>- Slick.jar<br /></li>
<li>- jinput.jar<br /></li>
<li>- lwjgl_util.jar (if want to use OpenGL's GLU class)</li></ul></li>
<li>Still in the 'Libraries' tab, click in the arrow next to lwjgl.jar</li>
<li>Double click 'Native library location'</li>
<li>Select folder that contain the natives (that were downloaded and extracted as instructed above)</li>
<li>Choose the folder of your Operating System:<br /><ul>
<li>- natives\windows folder which contains the *.dll files,<br /></li>
<li>- natives/macosx which contains the *.dylib and *.jnilib<br /></li>
<li>- natives/linux which contains the *.so</li></ul></li>
<li>(Optional) Expand slick.jar and edit the javadoc location to http://www.slick2d.org/javadoc/</li>
</ol>
