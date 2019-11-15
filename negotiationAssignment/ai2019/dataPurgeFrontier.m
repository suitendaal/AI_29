function [] = dataPurgeFrontier(bid)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here

%% init
data1=table2array(bid);
%% Find (pareto optimal) frontier
Sdata1=sort(data1,1,'descend'); %sort on ultility of ownBids
% Sdata1=sort(data1,1); %sort on ultility of ownBids
L=length(Sdata1);

if L==0 %% incase data loading went bad
else  %% no problems continue
for i=1:L %iterate over all points
    test=0;maxer=Sdata1(i,3);count=1;
    for j=i:L %search for points higher beyond point
        if Sdata1(j,3)>= maxer
            maxer=Sdata1(j,3);
            index=j;
            test=1;
        end
    end        
    if test==1 %if iteration found point, add to new data
        data1New(count,:)=Sdata1(index,:);
        count=count+1;
    end
end 

end
%%
figure;
plot(data1New(:,1),data1New(:,3),"*"); title("optimal fortier");xlabel("utility 1");ylabel("utitlity2");
%%
figure;
plot(Sdata1(:,1),Sdata1(:,3)); title("sorted data");

end

