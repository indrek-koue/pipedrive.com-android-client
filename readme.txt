This is Aadroid test client for sales management software www.pipedrive.com


The assignment: 
Create an Android client using https://developers.pipedrive.com/v1

Goals:
*Login (using username and password)
*user session saved for later
*show pipedrive contacts, 
*ability to open a contact for detailed view (with pagination)



Remarks from the developer:
*UI is very very primitive, emphasis was put on correctesness and functionality of the application.

*No consistent requests. Every time an activity is killed-created the request is redone. Currently it isn't a problem becuse the data amounts are small. If the request need to survive the configuration changes, then that would be solvable using fragment without UI which would hold reference to the downloader.



If you don't have Pipedrive account and To use the .apk: a8316021@drdrb.net:U52qcSWS