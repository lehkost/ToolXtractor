from urllib import request
import urllib
import json

url = "http://tapor.ca/api/tools/by_analysis"
response = urllib.request.urlopen(url)
data = json.loads(response.read())

tapor = list()
for tool in data['tools']:
    tapor.append(tool['name'])

final_tapor = list(set(tapor))

with open('tools_tapor.txt', 'w') as filehandle:
    for listitem in final_tapor:
        filehandle.write('%s\n' % listitem)
