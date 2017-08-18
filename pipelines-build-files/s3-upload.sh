#!/usr/bin/env bash
#get the war file name and the version (for aws deployment) from the target directory
#we assume there will only be one war file
file=$(ls build/libs/*.war)
filename=$(basename $file)
commit_hash=`git rev-parse --short HEAD`
branch=`git rev-parse --abbrev-ref HEAD`
if [ "$branch" = "master" ]; then
    label="church-connect-api-$commit_hash-`date +%H%M%S`"
else
    label="church-connect-api-$branch-$commit_hash-`date +%H%M%S`"
fi
$SCRIPTS_DIR/upload.sh $file $label
rm $file #remove the existing war file so that the next upload works.
