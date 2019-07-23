Develop a standalone application for processing openCellID files and storing its data to DB

Basic requirements: 
- CentOS 7 
- Java 8 
- PostgreSQL 9.5

Input: 
- openCellID file of UAE https://opencellid.org/downloads.php?token=e785b1aeedc128

Application requirements:

- Level 1:
	- Checks input folder for openCellID file
	- Parses openCellID file
	- Stores processed data into the PostgreSQL database with following conditions:
		- enable filtering by ranges (e.g., insert records with updated value > t0 and < t1 only)
		- enable filtering by list (e.g., insert records with type 3G or LTE only)
		- enable open ranges filtering (e.g., insert records with radius < 30000)
	- Provides statistics (lines, failed, not in filter, stored, etc.)
	- Provides performance statistics (lines/sec processed, records/sec inserted, etc.)

- Level 2:
	- Multi-threaded (multiple DB writers)
	- Configuration file contains database connection parameters, threads limiting, etc.

- Level 3:
	- Clusters stations by location and populates another table with clustered towers
	- (If have any UI experience) Implement a basic UI to show stored stations on the map
