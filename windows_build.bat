call windows/crear_db.bat
call mvn clean
call mvn install
call windows/generar_fixture.bat
start "target" OPF5Application.exe
