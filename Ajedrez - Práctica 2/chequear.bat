if not exist "doccheck" mkdir doccheck

javadoc ^
-private ^
-doclet com.sun.tools.doclets.doccheck.DocCheck ^
-docletpath .\lib\doccheck.jar ^
-encoding UTF-8 ^
-sourcepath .\src;.\test ^
-classpath .\lib\*;.\bin ^
-d doccheck ^
-subpackages juego
