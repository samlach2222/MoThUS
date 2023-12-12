# This script has been tested with PowerShell Core 7
# Although it should works with PowerShell 5.1, it has not been tested

param (
    [Parameter(Position = 0, Mandatory,
        HelpMessage = 'Language folder name in the HTML format (like fr, en-US, etc.)')]
    [string]$languageHtmlFormat,

    [Parameter(Position = 1, Mandatory,
        HelpMessage = 'Minimum number of elements')]
    [int]$minimumNbElements,

    [Parameter(Position = 2,
        HelpMessage = 'Absolute or relative path (from either language folder or current folder) to the full dictionary file')]
    [string]$fullDictionaryPath
)

Push-Location $PSScriptRoot\src\main\resources\static\assets\dictionaries

## INITIALIZATION : Check param values

# In case the path is relative, try to get content before changing working directory
if ($fullDictionaryPath) {
    $fullDictionary = Get-Content $fullDictionaryPath 2> $null
}

# Check if folder $languageHtmlFormat exists
if (-not (Test-Path -PathType Container $languageHtmlFormat)) {
    if ($fullDictionary) {
        Write-Output "Creating folder $languageHtmlFormat"
        New-Item -Path $languageHtmlFormat -ItemType Directory
    }
    else {
        if ($fullDictionaryPath) {
            Write-Error "Folder $languageHtmlFormat and file $fullDictionaryPath do not exist"
            Pop-Location
            exit 1
        }
        else {
            Write-Error "Folder $languageHtmlFormat does not exist and no fullDictionaryPath was provided"
            Pop-Location
            exit 2
        }
    }
}

if (-not ($fullDictionaryPath)) {
    $fullDictionaryPath = '.\full_dictionary.txt'
}

## INITIALIZATION : Set initial values

# Change working directory to this script location
Set-Location $languageHtmlFormat

# Try (again) to get content, this time from the language folder
if (-not ($fullDictionary)) {
    if ((Test-Path -PathType Leaf $fullDictionaryPath)) {
        $fullDictionary = Get-Content $fullDictionaryPath
    }
    if (-not ($fullDictionary)) {
        Write-Error "File $fullDictionaryPath does not exist"
        Pop-Location
        exit 11
    }
}

$nbLines = $fullDictionary.Length
$currentBegin = $null  # First element of current words
$words = $null  # String builder to store words until encountering a new first element

## MAIN PROGRAM : For each new first element, create a new file and fill it with the words until encountering a new first element

# For each line in the full dictionary
for ($i = 0; $i -lt $nbLines; $i++) {
    $line = $fullDictionary[$i]

    # Update progress bar every 500 lines (to not slow down the script)
    if ($i % 500 -eq 0) {
        [sbyte]$percentage = ((($i + 1) / $nbLines) * 100)
        Write-Progress -Activity 'Generating per file dictionary' -Status "$i/$nbLines ($currentBegin)" -PercentComplete $percentage
    }

    # Go to next iteration if the word doesn't match the minimum number of elements
    if (-not ($line -cmatch "^([A-Z][a-z]?){$minimumNbElements,}$")) {
        continue
    }

    # If the first element is new, add current words to a file and create a new string builder
    if ((-not ($line.StartsWith($currentBegin))) -or (-not ($currentBegin))) {
        # Add all current words to the file (it will automatically create/truncate the file)
        if ($currentBegin) {
            $words.ToString() | Out-File "$currentBegin.txt"
        }

        if ($line[1] -cmatch '[A-Z]') {
            $currentBegin = $line[0]
        }
        elseif (($line[2] -cmatch '[A-Z]') -or ($null -eq $line[2])) {
            $currentBegin = $line[0] + $line[1]
        }
        else {
            # If neither the second or third character is uppercase,
            # the dictionary is wrongly formated
            Write-Error "Word $line at line $($i + 1) is wrongly formated"
            Pop-Location
            exit 101
        }

        $words = [System.Text.StringBuilder]::new()

        # Update progress bar every time there is a new first element
        [sbyte]$percentage = ((($i + 1) / $nbLines) * 100)
        Write-Progress -Activity 'Generating per file dictionary' -Status "$i/$nbLines ($currentBegin)" -PercentComplete $percentage
    }

    # Add current word to the string builder
    $words.AppendLine($line) > $null
}
# Add remaining words
if ($words.ToString()) {
    $words.ToString() | Out-File "$currentBegin.txt"
}

Write-Progress -Activity 'Generating per file dictionary' -Completed

Pop-Location