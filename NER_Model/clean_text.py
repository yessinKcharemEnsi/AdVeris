import re


def clean_text (text) :
    clean = re.sub(r"http\S+", "", text)
    clean = re.sub(r"www\S+", "", text)

    clean = re.sub(r"""
                [!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]+  # Accept one or more copies of punctuation
                \ *           # plus zero or more copies of a space,
                """,
                " ",          # and replace it with a single space
                clean, flags=re.VERBOSE)


    return clean 