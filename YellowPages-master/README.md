# YellowPages
The following examples show how to use the Yellow Pages service provided by the DF (Directory Facilitator) agent.

>>> DFRegisterAgent
 
Description:
This example shows how to register an application specific service in the Yellow Pages catalogue managed by the DF Agent so that other agents can dynamically discover it. In this case in particular we register a “Weather-forecast” service for Italy. The name of this service is specified as a command line argument.This example should be used together with the DFSearchAgent and the DFSubscribeAgent as described below.

Start the JADE runtime (with the management GUI) and launch a DFRegisterAgent on it. Also specify a name for the service to register (e.g. “my-forecast”) as an argument. 
The command line could be java jade.Boot -gui provider:examples.yellowPages.DFRegisterAgent(my-forecast)
Then, from another window (or from the management GUI), launch a DFSearchAgent.
java jade.Boot -container searcher:examples.yellowPages.DFSearchAgent
The DFSearchAgent should find the service registered by the DFRegisterAgent.
After that, from another window (or from the management GUI), launch a DFSubscribeAgent.
java jade.Boot -container subscriber:examples.yellowPages.DFSubscribeAgent
The DFSubscribeAgent should immediately be notified about the service registered by the DFRegisterAgent.
Finally, from another window (or from the management GUI), launch another DFRegisterAgent.
java jade.Boot -container provider-1:examples.yellowPages.DFRegisterAgent(“forecast-1″)
The DFSubsribeAgent should automatically be notified about the newly registered service.

>>> DFSearchAgent

Description:
This example shows how to search for services provided by other agents and advertised in the Yellow Pages catalogue managed by the DF agent. In this case in particular we search for agents providing a “Weather-forecast” service.This example should be used together with the DFRegisterAgent and the DFSubscribeAgent as described here.

>>> DFSubscribeAgent

Description:
This example shows how to subscribe to the DF agent in order to be notified each time a given service is published in the yellow pages catalogue. In this case in particular we want to be informed whenever a service of type “Weather-forecast” for Italy becomes available.This example should be used together with the DFRegisterAgent and the DFSearchAgent.


>>> Sub-DF

Description:
This examples shows how to set up multiple Agent Domains by means of a network of connected (federated) Directory Facilitator Agents.
In particular this DF agent plays the role of a sub-df by automatically registering with a parent DF. This “SUBDF” inherits all the functionalities of the default DF, including its GUI. Once federated, the two DF agents provide recursive search capabilities. 
Notice that the same might be done without writing any line of code: just start a new agent subdf of classe jade.domain.df (subdf:jade.domain.df), send it a message to request to show the GUI (by using the dummyAgent), and then use its GUI to federate with its parent.
