#! /bin/sh
# Simple file to rebuild container

 echo "trying to stop container"
 podman stop k2m
 
 echo "trying to delete container"
 podman rm k2m
 
 echo "trying to delete image"
 podman rmi k2m
 
 echo "building container"
 podman build . -t k2m