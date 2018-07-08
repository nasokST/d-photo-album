package com.dphotoalbum.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class DPhotoAlbumCategory_sol_DPhotoAlbumCategory extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506040516040806109be83398101604052805160209091015160018054600160a060020a03199081163317825560028054600160a060020a03909416939091169290921790915560009190915560035561094f8061006f6000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631d6d3b7a811461009d57806323edf697146100d25780633164478f1461010c5780634240de2c1461015757806348c311711461017e57806354fd4d50146102b65780638da5cb5b146102cb578063c892a913146102fc578063d486028414610320575b600080fd5b3480156100a957600080fd5b506100d0600160a060020a036004351660243560443560ff60643581169060843516610335565b005b3480156100de57600080fd5b506100ea600435610417565b6040805193845260ff9283166020850152911682820152519081900360600190f35b34801561011857600080fd5b50610124600435610476565b60408051600160a060020a039095168552602085019390935260ff91821684840152166060830152519081900360800190f35b34801561016357600080fd5b5061016c61056a565b60408051918252519081900360200190f35b34801561018a57600080fd5b50610193610570565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019060200280838360005b838110156101df5781810151838201526020016101c7565b50505050905001858103845288818151815260200191508051906020019060200280838360005b8381101561021e578181015183820152602001610206565b50505050905001858103835287818151815260200191508051906020019060200280838360005b8381101561025d578181015183820152602001610245565b50505050905001858103825286818151815260200191508051906020019060200280838360005b8381101561029c578181015183820152602001610284565b505050509050019850505050505050505060405180910390f35b3480156102c257600080fd5b5061016c61074b565b3480156102d757600080fd5b506102e0610751565b60408051600160a060020a039092168252519081900360200190f35b34801561030857600080fd5b5061016c60043560ff60243581169060443516610760565b34801561032c57600080fd5b506102e0610914565b600154600160a060020a03163314806103585750600254600160a060020a031633145b151561036357600080fd5b600454841061037157600080fd5b6040805160608101825284815260ff848116602080840191825285831684860190815260008a81526008835286812095518655925160019095018054915160ff199092169585169590951761ff00191661010091909416029290921790925590548251600160a060020a038916815291820152815186927f1ff20e30c9a9fc163c7f565155d8539bb4da2fd3abae3bf1eaa3e9ab9dae2e2b928290030190a25050505050565b60015460009081908190600160a060020a03163314806104415750600254600160a060020a031633145b151561044c57600080fd5b50505060009081526008602052604090208054600190910154909160ff8083169261010090041690565b600154600090819081908190600160a060020a03163314806104a25750600254600160a060020a031633145b15156104ad57600080fd5b60075485106104bb57600080fd5b60078054869081106104c957fe5b60009182526020909120015460048054600160a060020a0390921691879081106104ef57fe5b600091825260209091200154600580548890811061050957fe5b90600052602060002090602091828204019190069054906101000a900460ff1660046002018881548110151561053b57fe5b90600052602060002090602091828204019190069054906101000a900460ff1693509350935093509193509193565b60005481565b600154606090819081908190600160a060020a031633148061059c5750600254600160a060020a031633145b15156105a757600080fd5b600780546040805160208084028201810190925282815260049260059260069291869183018282801561060357602002820191906000526020600020905b8154600160a060020a031681526001909101906020018083116105e5575b505050505093508280548060200260200160405190810160405280929190818152602001828054801561065657602002820191906000526020600020905b81548152600190910190602001808311610641575b50505050509250818054806020026020016040519081016040528092919081815260200182805480156106c657602002820191906000526020600020906000905b825461010083900a900460ff168152602060019283018181049485019490930390920291018084116106975790505b505050505091508080548060200260200160405190810160405280929190818152602001828054801561073657602002820191906000526020600020906000905b825461010083900a900460ff168152602060019283018181049485019490930390920291018084116107075790505b50505050509050935093509350935090919293565b60035481565b600154600160a060020a031681565b600154600090600160a060020a03163314806107865750600254600160a060020a031633145b151561079157600080fd5b60048054600181810183557f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b90910186905560058054808301825560208082047f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db001805460ff808b16601f95861661010090810a9182029183021990931617909255600680548088019091559283047ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f0180548a84169490951690910a92830292909102199092161790556007805492830181556000527fa66cc928b5edb82af9bd49922954155ab7b0942694bea4ce44661d9a8736c688909101805473ffffffffffffffffffffffffffffffffffffffff191632179055549054146108b657600080fd5b600554600654146108c657600080fd5b6004546000546040805191825251600019909201917f3c67ae0a0159fd7c0d9a90fd8ec3a713802f5d0aca87e18c0403f6b63166a6cf9181900360200190a250600454600019019392505050565b600254600160a060020a0316815600a165627a7a723058201aab2eddc612a4dfeeb169b7ea0d9e18e4f765f2315b6b241b1a6fc87e898af00029";

    public static final String FUNC_ADDCOMMENT = "addComment";

    public static final String FUNC_GETCOMMENTS = "getComments";

    public static final String FUNC_GETPHOTOBYINDEX = "getPhotoByIndex";

    public static final String FUNC_CATEGORY_ID = "category_id";

    public static final String FUNC_GETALLPHOTOS = "getAllPhotos";

    public static final String FUNC_VERSION = "version";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ADDPHOTO = "addPhoto";

    public static final String FUNC_ALBUM = "album";

    public static final Event NEWPHOTO_EVENT = new Event("NewPhoto", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event NEWCOMMENT_EVENT = new Event("NewComment", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected DPhotoAlbumCategory_sol_DPhotoAlbumCategory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DPhotoAlbumCategory_sol_DPhotoAlbumCategory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> addComment(String _from, BigInteger _to_photo_idx, byte[] _digest, BigInteger _hash_function, BigInteger _size) {
        final Function function = new Function(
                FUNC_ADDCOMMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.generated.Uint256(_to_photo_idx), 
                new org.web3j.abi.datatypes.generated.Bytes32(_digest), 
                new org.web3j.abi.datatypes.generated.Uint8(_hash_function), 
                new org.web3j.abi.datatypes.generated.Uint8(_size)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<byte[], BigInteger, BigInteger>> getComments(BigInteger _for_photo_idx) {
        final Function function = new Function(FUNC_GETCOMMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_for_photo_idx)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}));
        return new RemoteCall<Tuple3<byte[], BigInteger, BigInteger>>(
                new Callable<Tuple3<byte[], BigInteger, BigInteger>>() {
                    @Override
                    public Tuple3<byte[], BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], BigInteger, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<Tuple4<String, byte[], BigInteger, BigInteger>> getPhotoByIndex(BigInteger _photo_index) {
        final Function function = new Function(FUNC_GETPHOTOBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_photo_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint8>() {}));
        return new RemoteCall<Tuple4<String, byte[], BigInteger, BigInteger>>(
                new Callable<Tuple4<String, byte[], BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, byte[], BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, byte[], BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> category_id() {
        final Function function = new Function(FUNC_CATEGORY_ID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>> getAllPhotos() {
        final Function function = new Function(FUNC_GETALLPHOTOS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}, new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint8>>() {}, new TypeReference<DynamicArray<Uint8>>() {}));
        return new RemoteCall<Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>>(
                new Callable<Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Address>) results.get(0).getValue()), 
                                convertToNative((List<Bytes32>) results.get(1).getValue()), 
                                convertToNative((List<Uint8>) results.get(2).getValue()), 
                                convertToNative((List<Uint8>) results.get(3).getValue()));
                    }
                });
    }

    public RemoteCall<BigInteger> version() {
        final Function function = new Function(FUNC_VERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> addPhoto(byte[] _digest, BigInteger _hash_function, BigInteger _size) {
        final Function function = new Function(
                FUNC_ADDPHOTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_digest), 
                new org.web3j.abi.datatypes.generated.Uint8(_hash_function), 
                new org.web3j.abi.datatypes.generated.Uint8(_size)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> album() {
        final Function function = new Function(FUNC_ALBUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<DPhotoAlbumCategory_sol_DPhotoAlbumCategory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger _category_id, String _album) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id), 
                new org.web3j.abi.datatypes.Address(_album)));
        return deployRemoteCall(DPhotoAlbumCategory_sol_DPhotoAlbumCategory.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<DPhotoAlbumCategory_sol_DPhotoAlbumCategory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger _category_id, String _album) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id), 
                new org.web3j.abi.datatypes.Address(_album)));
        return deployRemoteCall(DPhotoAlbumCategory_sol_DPhotoAlbumCategory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public List<NewPhotoEventResponse> getNewPhotoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWPHOTO_EVENT, transactionReceipt);
        ArrayList<NewPhotoEventResponse> responses = new ArrayList<NewPhotoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewPhotoEventResponse typedResponse = new NewPhotoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._photo_index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._category_id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewPhotoEventResponse> newPhotoEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, NewPhotoEventResponse>() {
            @Override
            public NewPhotoEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWPHOTO_EVENT, log);
                NewPhotoEventResponse typedResponse = new NewPhotoEventResponse();
                typedResponse.log = log;
                typedResponse._photo_index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._category_id = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<NewPhotoEventResponse> newPhotoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWPHOTO_EVENT));
        return newPhotoEventObservable(filter);
    }

    public List<NewCommentEventResponse> getNewCommentEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWCOMMENT_EVENT, transactionReceipt);
        ArrayList<NewCommentEventResponse> responses = new ArrayList<NewCommentEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewCommentEventResponse typedResponse = new NewCommentEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._photo_index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._category_id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<NewCommentEventResponse> newCommentEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, NewCommentEventResponse>() {
            @Override
            public NewCommentEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWCOMMENT_EVENT, log);
                NewCommentEventResponse typedResponse = new NewCommentEventResponse();
                typedResponse.log = log;
                typedResponse._photo_index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._category_id = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<NewCommentEventResponse> newCommentEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWCOMMENT_EVENT));
        return newCommentEventObservable(filter);
    }

    public static DPhotoAlbumCategory_sol_DPhotoAlbumCategory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DPhotoAlbumCategory_sol_DPhotoAlbumCategory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static DPhotoAlbumCategory_sol_DPhotoAlbumCategory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DPhotoAlbumCategory_sol_DPhotoAlbumCategory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class NewPhotoEventResponse {
        public Log log;

        public BigInteger _photo_index;

        public BigInteger _category_id;
    }

    public static class NewCommentEventResponse {
        public Log log;

        public BigInteger _photo_index;

        public String _from;

        public BigInteger _category_id;
    }
}
