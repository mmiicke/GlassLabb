
#Install package mlbench
install.packages("mlbench")
install.packages("gmodels")

#Install library mlbench
library(mlbench)
library(class)


# makes it a dataSet so i can run some different tasks
data(Glass)


#Start för varje körning
summary(Glass) # check out ?Glass
nrow(Glass)
##Normalisering
#normalize <- function(x){
 # num <- x - min(x)
  #denom <- max(x) - min(x)
  #return(num/denom)
#}
#Normalizes the Glass data
#Glass_norm <- as.data.frame(lapply(Glass[1:9], normalize))


#Summarize
##Use Glass for neutral data
##USe Glass_norm for normalized data
##Use res.std for standarized data
summary(Glass)

#plot, pairwise
pairs(Glass[1:9], main = "Glass", pch = 21, bg = c("red", "green3", "blue")[unclass(Glass$Species)])


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
#classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=3)
classifiedData <- knn(train = Glass.training, test = Glass.test, cl = Glass.trainLabels, k=5)
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

##-------Standardisering av data-------------------##
standard.features <-scale(Glass[,9:1])
standardData <- cbind(standard.features,Glass[10])
anyNA(standardData)
head(standardData)

#Delar upp i olika delar
standard66 <- sample(1:nrow(standardData),as.integer(0.66*nrow(standardData)))
standard75 <- sample(1:nrow(standardData),as.integer(0.75*nrow(standardData)))
standard50 <- sample(1:nrow(standardData),as.integer(0.50*nrow(standardData)))

#traing 2/3 and test 1/3
standardTraining66 <- standardData[standard66]
standardTest66 <- standardData[-standard66]

#Training 75% and test 25
sample <- sample.split(data$Type,splitRatio = 0.75)
standardTraining75p <- standardData(data, sample==TRUE)
standardTest75p <- standardDara(data, sample==FALSE)

#Knn() k=3
prdedicted.type <- knn(standardTraining75p[1:9],standardTest75p[1:9],standardTraining75p$Type,k=3)
#error in prediction
error <- mean(predicted.type!=standardTest75p$Type)
#Confusion matrix
confusionMatrix(predicted.type,standardTest75p$Type)



