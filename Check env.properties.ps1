cls
$PWD_FORWARD_SLASH = $pwd -replace '\\','/'
$REQUIRED_FIELDS_ERR = "Check file:///$PWD_FORWARD_SLASH/src/main/resources/application.properties for the required fields, for example:`nDB_USER=root`nMAIL_PORT=587`n..."
if (-not (Test-Path -PathType Leaf 'src\main\resources\env.properties')) {
    $Host.UI.WriteErrorLine("File `src/main/resources/env.properties` missing, aborting...`n`n$REQUIRED_FIELDS_ERR")
    exit -1
}
$fieldsExpected = Select-String -Path 'src\main\resources\application.properties' -Pattern '^[^#][^\$]+\${([^}]+)}' | ForEach-Object {$_.Matches.Groups[1].Value}
$fieldsPresent = Get-Content 'src\main\resources\env.properties' 2> $null | ConvertFrom-String -Delimiter '=' | Select-Object -ExpandProperty P1
if (($fieldsExpected | Where-Object {$_ -notin $fieldsPresent}).Count -ne 0) {
    $Host.UI.WriteErrorLine("Missing fields in file:///$PWD_FORWARD_SLASH/src/main/resources/env.properties`, aborting...`n`n$REQUIRED_FIELDS_ERR")
    exit -2
}