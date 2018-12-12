echo $key | base64 --decode >> key
chmod 700 key
GIT_SSH_COMMAND='ssh -i key' git push --force git+ssh://git@push-par-clevercloud-customers.services.clever-cloud.com/app_b58bad46-6dd3-49c3-a3c2-26aee5df0565.git master
rm key
