#-------Rensa skript------#
rm(list=ls())
graphics.off()

#Install package mlbench
install.packages("mlbench")
install.packages("class")
install.packages("gmodels")
install.packages("caTools")
install.packages("dplyr")
install.packages('e1071', dependencies=TRUE)

#Install library mlbench
library(mlbench)
library(class)
library(caTools)
library(caret)
library(dplyr)

#--------------{Data utan norm eller std}---------------#

# makes it a dataSet so i can run some different tasks
data(Glass)


#Start för varje körning
summary(Glass) # check out ?Glass
nrow(Glass)

#Summarize
##Use Glass for neutral data
##USe Glass_norm for normalized data
##Use res.std for standarized data
summary(Glass)

#plot, pairwise
pairs(Glass[1:9], main = "Glass", pch = 21, bg = c("red", "green3", "blue")[unclass(Glass$Type)])


#set.seed(456)#987
## split in two sets, 2/3 which will be the training data and 1/3 for the test data
## The training data is (for the purpose of splitting) labled 1, and the test data 2
ind <- sample(2, nrow(Glass), replace=TRUE, prob=c(2/3, 1/3))
## did we get the distribution ~2/3 and 1/3?


length(ind[ind==1]) #training data 
length(ind[ind==2]) #test data
nrow(Glass)#Antal rader i data


## create the training vectors and test vectors
Glass.training <- Glass[ind==1, 1:9]
Glass.test <- Glass[ind==2, 1:9] 


## create the target values, i.e species=Klasser Label=feature
Glass.trainLabels <- Glass[ind==1, 10]
Glass.testLabels <- Glass[ind==2, 10]


## if we want to examine the all data. Coupled with its' targets!
data.frame(Glass.training,Glass.trainLabels)


## check out what knn needs and what it does
#?knn()
##Ändrar K-värdet för att se om det är någon skillnad på 'Accuracy' 3,7,9
classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=3)
#classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=5)
#classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=7)


# what did we put in as training data to the kNN classifier?
data.frame(Glass.training, Glass.trainLabels)



### Assess the performance of the kNN classifier ##
# How did the kNN classifier perform for each class, i.e specie
confusion <- table(Target = Glass.testLabels, Predicted = classifiedData)
confusion


# Compute the classification accuracy: The number of correct predictions made 
#   divided by the total number of predictions made * 100 to turn it into a percentage
accuracy <- (sum(diag(confusion))/sum(confusion) )* 100
accuracy

#--------------------------{Normaliserad data}---------------------------#

# makes it a dataSet so i can run some different tasks
data(Glass)


normalize <- function(x){
  num <- x - min(x)
  denom <- max(x) - min(x)
  return(num/denom)
}
#Normalizes the Glass data
Glass <- as.data.frame(lapply(Glass[1:9], normalize))

print(Glass)

#Start för varje körning
summary(Glass) # check out ?Glass_norm
nrow(Glass)

#Summarize
##Use Glass for neutral data
##USe Glass_norm for normalized data

summary(Glass)

#plot, pairwise
#pairs(Glass[1:9], main = "Glass", pch = 21, bg = c("red", "green3", "blue")[unclass(Glass$Type)])


#set.seed(456)#987
## split in two sets, 2/3 which will be the training data and 1/3 for the test data
## The training data is (for the purpose of splitting) labled 1, and the test data 2
##Splits it even in 75% training 25% test, 50% training and 50% test

ind <- sample(2, nrow(Glass), replace=TRUE, prob=c(2/3, 1/3))
#ind <- sample(2,nrow(Glass),replace=TRUE, prob=c(0.75,0.25))
#ind <- sample(2,nrow(Glass),replace=TRUE, prob=c(0.50,0.50))
## did we get the distribution ~2/3 and 1/3?


length(ind[ind==1]) #training data 
length(ind[ind==2]) #test data
nrow(Glass)#Antal rader i data


## create the training vectors and test vectors
Glass.training <- Glass[ind==1, 1:9]
Glass.test <- Glass[ind==2, 1:9] 

## create the target values, i.e species=Klasser Label=feature
Glass.trainLabels <- Glass[ind==1, 10]
Glass.testLabels <- Glass[ind==2, 10]


## if we want to examine the all data. Coupled with its' targets!
data.frame(Glass.training,Glass.trainLabels)


## check out what knn needs and what it does
#?knn()
##Ändrar K-värdet för att se om det är någon skillnad på 'Accuracy' 3,7,9
classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=3)
#classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=5)
#classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=7)


# what did we put in as training data to the kNN classifier?
data.frame(Glass.training, Glass.trainLabels)



### Assess the performance of the kNN classifier ##
# How did the kNN classifier perform for each class, i.e specie
confusion <- table(Target = Glass.testLabels, Predicted = classifiedData)
confusion


# Compute the classification accuracy: The number of correct predictions made 
#   divided by the total number of predictions made * 100 to turn it into a percentage
accuracy <- (sum(diag(confusion))/sum(confusion) )* 100
accuracy

#--------------------{Standardiserad data}---------------------#

col.names=c("RI","Na","Mg","Al","Si","K","Ca","Ba","Fe","Type")
#standardiserar datat
standard.features <- scale(Glass[,1:9])

#binder samman det standardiserade datat med target kolumnerna
data <- cbind(standard.features,Glass[10])
#Kollar om det finns några missade värden
anyNA(data)

#Set ur som att 'data' är fritt från NA´s
head(data)

#Skapar ett träningsset och ett testset
sample <- sample.split(data$Type,SplitRatio = 0.75)
train <- subset(data,sample==TRUE)
test <- subset(data,sample==FALSE)

#kör knn med k-värde 3
predicted.type <- knn(train[1:9],test[1:9],train$Type,k=3)
error <- mean(predicted.type!=test$Type)
confusionMatrix(predicted.type,test$Type)

