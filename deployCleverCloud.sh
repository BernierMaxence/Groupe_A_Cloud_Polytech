echo $key | base64 --decode >> key
eval "$(ssh-agent -s)"
chmod 600 key
ssh-add key
git remote add deploy git+ssh://git@push-par-clevercloud-customers.services.clever-cloud.com/app_b58bad46-6dd3-49c3-a3c2-26aee5df0565.git
git push deploy master
rm key
