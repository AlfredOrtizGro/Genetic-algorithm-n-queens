_______________________________________________________________________________

                            UploadFilesDpx
                    Carga de archivos del SO a Dropbox
_______________________________________________________________________________

    - Autor : Jonathan Rojas Simón
    - Correo: ids_jonathan_rojas@hotmail.com
_______________________________________________________________________________

Este componete permite recuperar un grupo de archivos que tienen en común una 
extensión específica (.txt, .png, .ppt, etc.) para subirlas a un directorio de 
Dropbox a través de su propia API. Para realizar este proceso se debe habilitar
el proceso de generación de Tokens de acceso (Ver descripción debajo) para
realizar la subida de archivos. 

_______________________________________________________________________________

SINTAXIS

-DIR:str [-EXT:str] -TOKEN:str [-UFDATE:str] [-OUT:str] -FILEURL:str

_______________________________________________________________________________

DESCRIPCIÓN

-DIR     -> Ruta y nombre del directorio que contienen los archivos a subir en 
            la cuenta de Dropbox.

-EXT     -> Cadena que especifica el conjunto de archivos que se subirán a 
            Dropbox por su extensión. En caso de no especificar esta etiqueta, 
            el componente buscará los archivos con la extensión (*.png) por 
            defecto.

-TOKEN   -> Especifica la clave de acceso a la aplicación, esta clave es 
            generada por medio de la generación de una app en Dropbox

            Para realizar una app de Dropbox se deben seguir los siguientes
            pasos:
              1.- Iniciar sesión en Dropbox
              2.- Ingresar a la siguiente dirección:
                    https://www.dropbox.com/developers
              3.- Seleccionar la opción "My apps" ubicado en el panel 
                  izquerdo de la página.
              4.- Seleccionar la opción "Create app". Después se abrirá 
                  otra página en donde se colocarán algunos datos de la app.
              5.- Seleccíonar la opción "Dropbox API", posteriormente se abrirá
                  la siguiente opción (2. Choose the type of access you need). 
                  En caso de que tus archivos se almacenen en un directorio 
                  predefinido, seleccionar "App folder". En caso de requerir 
                  que los archivos se suban en cualquier carpeta de Dropbox, 
                  seleccionar "Full Dropbox".
              6.- Al final se abrirá el apartado "3. Name your app" donde 
                  se deberá escribir el nombre de la aplicación.
              7.- Seleccionar "Create app"

             Una vez que se haya creado la app, se podrá extraer el token de 
             acceso, para extraerlo se siguen los siguientes pasos:

             1.- Seleccionar la aplicación generada
             2.- Ubicar el apartado "OAuth 2", el cual contiene la opción 
                 "Generated access token" para seleccionar el botón "Generate".
                 Una vez seleccionada esta opción se deberá copiar el enlace
                 generado para colocarlo como argumento del parámetro -TOKEN

-UFDATE  -> Especifica la fecha en donde se concatenará al directorio
            especificado de -OUT. El formato de fecha que maneja es el 
            siguiente:
                          AAAA/MM/DD/HH/mm

-OUT	 -> Nombre del directorio (o grupo de subdirectorios) donde se 
            almacenarán los archivos a subir en Dropbox. En caso de que los
            directorios no se hayan creado, el componente generará todos 
            los subdirectorios asociados.

-FILEURL -> Ruta y nombre del archivo donde se almacena la URL compartida para
            acceder 
