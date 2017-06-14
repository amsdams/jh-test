cp ./src/main/resources/config/liquibase/master.xml \
	./src/main/resources/config/liquibase/master.txt
npm install -g generator-jhipster-elasticsearch-reindexer
npm install -g generator-jhipster-nav-element

yo jhipster --force --with-entities

yo jhipster:import-jdl  test6.jh
