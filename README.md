# Convert to correct video format 

### Using this variant (for h264 codec)

ffmpeg -i '.\input.mkv' -codec copy -vcodec libx264 'output.mp4' 

can we do this on initial startup?? 

### Notes

Build notflix

```bash
docker build -t jameswagstaff/notflix:potato --platform linux/arm64/v8 .
```

update mongo version for arm v1 chipset -> mongo:4.4.18-focal

mkdir /media

sudo mount /dev/sdc1 /media
the config will now be /media/media

## TODO
1. add volume to mongo config
2. detect when new media and validate format, 1 jpg and 1 mp4 with libx264 codec

192.168.1.222 ubuntu:james-ubuntu

