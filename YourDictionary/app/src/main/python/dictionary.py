import requests

def searchedFor(txt):
    txt = str(txt)
    url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + txt.lower()

    try:
        meaning = requests.get(url).json()

        if 'meanings' in meaning[0]:
            meanings = meaning[0]['meanings']
            if meanings:
                # Check if 'definitions' exist in the first meaning
                if 'definitions' in meanings[0]:
                    definition = meanings[0]['definitions'][0]['definition']
                else:
                    definition = "No definition found"

                # Check if 'partOfSpeech' exists in the first meaning
                if 'partOfSpeech' in meanings[0]:
                    part_of_speech = meanings[0]['partOfSpeech']
                else:
                    part_of_speech = "Part of speech not available"

                # Check if 'synonyms' exist in the first meaning
                if 'synonyms' in meanings[0]:
                    synonyms = meanings[0]['synonyms']
                    if synonyms:
                        first_synonym = synonyms[0]
                    else:
                        first_synonym = "No synonyms found"
                else:
                    first_synonym = "Synonyms not available"

                # Check if 'antonyms' exist in the first meaning
                if 'antonyms' in meanings[0]:
                    antonyms = meanings[0]['antonyms']
                    if antonyms:
                        first_antonym = antonyms[0]
                    else:
                        first_antonym = "No antonyms found"
                else:
                    first_antonym = "Antonyms not available"

                # Assign the values to 'ans' variable
                ans = (definition, part_of_speech, first_synonym, first_antonym)
                return ans

        # If no meanings were found, return indications for all values as 'ans'
        ans = ("No data found", "No data found", "No data found", "No data found")
        return ans

    except requests.exceptions.RequestException as e:
        # Handle exceptions related to network errors or invalid URLs
        print(f"Error: {e}")
        ans = (None, None, None, None)
        return ans

    except (IndexError, KeyError) as e:
        # Handle exceptions related to JSON parsing or missing data
        print("Error: Unable to parse API response.")
        ans = (None, None, None, None)
        return ans
