#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Generating scaladoc and coverage report.\n"

  sbt doc
  sbt coverageReport

  echo -e "Publishing scaladoc and coverage report.\n"

  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/inet-oxford/commons-sandbox gh-pages > /dev/null

  cd gh-pages

  # get rid of old docs and coverage stats
  git rm -rf ./docs
  git rm -rf ./coverage

  # copy over the new docs
  mkdir -p ./docs/api/latest
  cp -Rf ../target/scala-2.11/api/* ./docs/api/latest

  # copy over the new coverage stats
  mkdir -p ./coverage
  cp -Rf ../target/scala-2.11/scoverage-report ./coverage

  # push to github!
  git add -f .
  git commit -m "Lastest doc and coverage report on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published scaladoc to gh-pages.\n"

fi