%%
[Agents1, Data1, TotalS1] = csvToData2("tour (1).csv");
[Agents2, Data2, TotalS2] = csvToData2("tour (2).csv");
[Agents3, Data3, TotalS3] = csvToData2("tour (3).csv");
[Agents4, Data4, TotalS4] = csvToData2("tour (4).csv");
[Agents5, Data5, TotalS5] = csvToData2("tour (5).csv");
[Agents6, Data6, TotalS6] = csvToData2("tour (6).csv");
[Agents7, Data7, TotalS7] = csvToData2("tour (7).csv");
[Agents8, Data8, TotalS8] = csvToData2("tour (8).csv");
[Agents9, Data9, TotalS9] = csvToData2("tour (9).csv");
[Agents10, Data10, TotalS10] = csvToData2("tour (10).csv");

TotalX={TotalS1,TotalS2,TotalS3,TotalS4,TotalS5...
    ,TotalS6,TotalS7,TotalS8,TotalS9,TotalS10};
%%
for i=1:1
    disp(i);
    d=TotalX{i};
    d(:,3:end)=strrep(d(:,3:end),",",".");
%     figure(i);hold on;
    figure(1);hold on;figure(2);hold on;figure(3);hold on;
    for j=1:length(d)

%         if d(:,1)=="Group29_BoaParty"
%             disp('hier');
%             figure(1);hold on;title("Min Max utility");
%             plot(double(d(:,3)));
%             plot(double(d(:,4)));
%             
%             figure(2);hold on;title("Dist from Nash");
%             plot(double(d(:,5)));
%             
%             figure(3);hold on;title("Dist from Pareto");
%             plot(double(d(:,6)));
%             
%         end
        if d(j,1)=="Group29_BoaParty"
            title("Min Max utility");
            plot(double(d(j,3)));
            plot(double(d(j,4)));
            
            title("Dist from Nash");
            plot(double(d(j,5)));
            
            title("Dist from Pareto");
            plot(double(d(j,6)));
            
        end
    end
    
end
