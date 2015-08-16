call windows/crear_db.bat
mvn clean
mvn install
call windows/generar_fixture.bat
start "target" OPF5Application.exe
