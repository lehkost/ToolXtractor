from bs4 import BeautifulSoup
import urllib.request

html_page = urllib.request.urlopen("https://dh2020.adho.org/abstracts/")
soup = BeautifulSoup(html_page, "html.parser")
for link in soup.findAll('a'):
    if link.get('href').startswith("https://dh2020.adho.org/wp-content/uploads/2020/07/"):
        url = link.get('href').replace(".html", ".xml")
        urllib.request.urlretrieve(url, './2020/' + url.rsplit('/', 1)[-1])
