import java.util.*;
import java.util.ArrayList;



public class 	multiway_tree
{
			
			int 	size=0;
			multiway_node  root= new multiway_node("root",0);
			
			
			ArrayList<multiway_node> input_list_main = new ArrayList<multiway_node>();	
				
			public void initialize_tree(ArrayList<String> input_list_string, ArrayList<String> dependency_list_main, ArrayList<String> duration_list)
			{
					// Initialize nodes from the arraylist of node names into actual nodes
					// Store these nodes in an arraylist of multiway nodes. 
					
					for (int i=0;i<input_list_string.size();i++)
					{
						multiway_node new_node = new multiway_node();
						new_node.name = input_list_string.get(i);
						input_list_main.add(new_node);
					}
					
					
					// initialize the string_dependencies array of each node. 
					for(int t=0;t<input_list_main.size();t++)
					{
						String temp[] = dependency_list_main.get(t).split(",");
						for (int u=0;u<temp.length;u++)
						{
							input_list_main.get(t).string_dependencies.add(temp[u]);
						}
					}
					
					// Initialize the durations of the nodes. 
					
					for (int i=0;i<duration_list.size();i++)
					{
						input_list_main.get(i).duration=Integer.parseInt(duration_list.get(i));
					}
					
					int i=0;
					
					// Go to each node in input_list_main
					
					while (i<input_list_main.size())
					{
						// Access the node's string_dependencies ArrayList
						multiway_node temp = input_list_main.get(i);
						ArrayList<String> temp_string_dependencies = temp.string_dependencies;
						
						// check each dep_string in string_dependencies with the names in input_list_main.
						for (int j=0;j<temp_string_dependencies.size();j++)
						{
							String dep_string = temp_string_dependencies.get(j);
							// If dep_string = "NULL" then we are currently looking at root node. 
							if (dep_string.equals("NULL"))
							{
								root.connections.add(temp);
								temp.dependencies.add(root);
							}
							
							// If not, then we iterate over the names of nodes in input_list_main
							else
							{
								for (int k=0; k<input_list_main.size();k++)
								{
									String node_name = input_list_main.get(k).name;
									// If match, add temp to connection of input_list_main.get(k)
									// and add input_list_main.get(k) to the dependency of temp.
									if (node_name.equals(dep_string))
									{
										temp.dependencies.add(input_list_main.get(k));
										input_list_main.get(k).connections.add(temp);
									}
								}
							}
						
						}
						i++;
					 }
					 
					 // 
					 
					 
					 
					 
					 
					

					
				
						
			}
					
			public void print_tree(multiway_node node)
			{
				if(node.connections.size()==0)
				{
					System.out.println(node.name);
					return;
				}
				
				else
				{
					System.out.println(node.name);
					int i=0;
					while(i<node.connections.size())
					{
						
						print_tree(node.connections.get(i));
						i++;
					}
				}
			}
			
			public void print_paths(multiway_node node,ArrayList<String> path,int sum,ArrayList<String> output_path)
			{
				// This method uses ArrayList path for storing the path string at the end of each recursion
				// We use the ArrayList output_path to return each path as a string to a method that is calling
				// output_path arraylist can be used later for sorting each path
				
				// we add and remove nodes that are visited multiple times during the forward and backward recursive calls 
				// to prevent repetition while listing paths. We use the same logic to keep track of the sum of the durations. 
				if(node.connections.size()==0)
				{
					path.add(node.name);
					sum = sum + (node.duration);
					String temp ="";

					for (int i=1;i<path.size();i++)
					{
						temp = temp+path.get(i)+":";					
					}
					temp = temp+ Integer.toString(sum);
					output_path.add(temp);
								
					path.remove(node.name);
					sum = sum-(node.duration);
					
					return;
				}
				
				else
				{
					int i=0;
					while(i<node.connections.size())
					{
						path.add(node.name);
						sum = sum + (node.duration);
						
						print_paths(node.connections.get(i),path,sum,output_path);
								
						path.remove(node.name);
						sum = sum - (node.duration);
						i++;
					}
				}
			}
		
}
			
			
			
		
		
		
		