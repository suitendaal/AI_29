%% keep it clean
close all; clear all; clc;fprintf("clean \n");
%% Import
% "touro.csv";
filename="tour (10).csv";
m = readtable(filename,...
    'Delimiter',';','ReadVariableNames',true);
% data1=table2array(bid42);
%% 
% agent1=m.Agent1(:;1:end-3)
% tester=split(m.Agent1,"@");
%%
% str=(m.Agent1(1));
% str=string(m.Agent1(1));
% str2=char(m.Agent1{1});
str=m.Agent1{1};
% newStr = extractBefore(str,"@")
newstr=extractBefore(str,"@");

%%
% Agent1=zeros(1,length(m.Agent1));
% Agent1=[];
for i=1:length(m.Agent1)
    str=m.Agent1{i};
    Agent1(i) = convertCharsToStrings(extractBefore(str,"@"));
    str=m.Agent2{i};
    Agent2(i) = convertCharsToStrings(extractBefore(str,"@"));
    str=m.Agent3{i};
    Agent3(i) = convertCharsToStrings(extractBefore(str,"@"));
end
Agents  = [Agent1; Agent2; Agent3];
AgentsI = [Agent1; Agent2; Agent3]';
Data    = [m.min_util_, m.max_util_, m.Dist_ToPareto, m.Dist_ToNash];
Total   = [AgentsI Data];
TotalS  = sort(Total,1);

%%
[Agents1, Data1, TotalS1] = csvToData("tour (1).csv");
[Agents2, Data2, TotalS2] = csvToData("tour (2).csv");
[Agents3, Data3, TotalS3] = csvToData("tour (3).csv");
[Agents4, Data4, TotalS4] = csvToData("tour (4).csv");
[Agents5, Data5, TotalS5] = csvToData("tour (5).csv");
[Agents6, Data6, TotalS6] = csvToData("tour (6).csv");
[Agents7, Data7, TotalS7] = csvToData("tour (7).csv");
[Agents8, Data8, TotalS8] = csvToData("tour (8).csv");
[Agents9, Data9, TotalS9] = csvToData("tour (9).csv");
[Agents10, Data10, TotalS10] = csvToData("tour (10).csv");

TotalX={TotalS1,TotalS2,TotalS3,TotalS4,TotalS5...
    ,TotalS6,TotalS7,TotalS8,TotalS9,TotalS10};
%%
for i=1:10
    i
    d=TotalX{i};
    for j=1:length(d)
        figure;
        if d(j,1)=="Group29_BoaParty"
            plot(double(d(j,4)));hold on;
            plot(double(d(j,5)));
        end
    end
    
end


