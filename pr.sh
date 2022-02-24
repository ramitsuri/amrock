############################################################
# Constants                                                #
############################################################
CREATE="Create"
UPDATE="Update"
REMOTE="origin"
TEAM="healthrhythms/hr-android"

############################################################
# Variables                                                #
############################################################
RunLintCheck=true
Squash=true
PRMode=$UPDATE
MainBranch="master"

############################################################
# Help                                                     #
############################################################
Help()
{
   # Display Help
   echo "This script will automate most of the PR create/update process for an Android project"
   echo "Including"
   echo "- running lint check"
   echo "- squashing commits against a main branch. This will bring you to the point where you diverged off of the main branch"
   echo "- commit changes. Will ask for commit message"
   echo "- push changes to remote"
   echo "- Create PR on GitHub. GitHub CLI should be installed and authenticated"
   echo
   echo "To use"
   echo "- put the script in the root of your Android project"
   echo "- edit the constants if necessary"
   echo "- install GitHub cli"
   echo "- create ssh key, create personal access token"
   echo "- run gh auth login"
   echo "- login to github.com, ssh, select new ssh key, login with browser"
   echo
   echo "Syntax: ./pr.sh [-l|s|c|u|b|h]"
   echo "options:"
   echo "l     Skip lint checks"
   echo "s     Skip squashing commits in this branch"
   echo "c     Will create a new PR with this branch"
   echo "u     Will update the existing PR. This is the default"
   echo "b     Set the main branch to squash commits against. Takes an argument. You can also update the MainBranch var in the script. Default is master"
   echo "h     Show help"
   echo
   echo "Examples"
   echo "./pr.sh"
   echo "This will run the lint check, squash commits against master branch, ask for commit message and push the changes to remote"
   echo
   echo "./pr.sh -lcb develop"
   echo "This will skip the lint check, squash commits against develop branch, ask for commit message, push the changes to remote and create a PR"
   echo
   echo "./pr.sh -su"
   echo "This will run the lint check, skip squashing commits, ask for commit message and push the changes to remote"
   echo
}

############################################################
# LintCheck                                                #
############################################################
LintCheck()
{
   echo "LintCheck Start"
   ./gradlew ktlintFormat
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   ./gradlew ktlintCheck
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   echo "LintCheck End"
}

############################################################
# SquashCommits                                            #
############################################################
SquashCommits()
{
   echo "SquashCommits Start against $MainBranch"
   current=$(git symbolic-ref HEAD)
   echo "Squashing all commits from $current"
   git reset $(git merge-base $MainBranch $current)
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   echo "SquashCommits End"
}

############################################################
# Commit                                                   #
############################################################
Commit()
{
   echo "Commit Start"
   git add -A
   git commit
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   echo "Commit End"
}

############################################################
# Push                                                     #
############################################################
Push()
{
   echo "Push Start"
   branch=$(git branch --show-current)
   git push -u origin $branch
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   echo "Push End"
}

############################################################
# Create PR                                                #
############################################################
CreatePR()
{
   echo "CreatePR Start"
   #gh pr create --head --fill --reviewer $TEAM
   #gh pr create --web
   gh pr create --fill
   retval=$?
   if [ $? -ne 0 ]
   then
     exit 0
   fi
   echo "CreatePR End"
}

############################################################
# Run                                                     #
############################################################
Run()
{
   if [ $RunLintCheck = true ]
   then
     LintCheck
   fi

   if [ $Squash = true ]
   then
     SquashCommits
   fi

   Commit

   Push

   if [ $PRMode == $CREATE ]
   then
     CreatePR
   fi
}

############################################################
############################################################
# Main program                                             #
############################################################
############################################################
while getopts "lscuhb:" option; do
   case $option in
      h) # display Help
         Help
         exit;;
      c) # Create PR
         PRMode=$CREATE;;
      u) # Update PR
         PRMode=$UPDATE;;
      l) # Lint check
         RunLintCheck=false;;
      s) # Squash commits
         Squash=false;;
      b) # Set main branch commits
         MainBranch=$OPTARG;;
      \?) # Invalid option
         echo "Error: Invalid option"
         exit;;
   esac
done

Run


############################################################
# SetMode  (Not used)                                      #
############################################################
SetMode()
{
   mode=$1
   if [ $mode == "C" ]
   then
      PRMode=$CREATE
   elif [ $mode == "U" ]
   then
      PRMode=$UPDATE
   else
      echo "Invalid PR mode. Should be C (for create) or U (for update)"
      exit
   fi
}