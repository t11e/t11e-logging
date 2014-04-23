This project contains Transparensee specific log formatters.

To cut a release:

  git tag 1.1
  git push origin 1.1
  mvn versions:set -DnewVersion=1.1
  mvn clean package
  mvn versions:set -DnewVersion=1.2-SNAPSHOT
  git add pom.xml
  git commit -m "Updates version to 1.2-SNAPSHOT"
  git push origin master
