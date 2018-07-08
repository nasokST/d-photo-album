package com.dphotoalbum.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class DPhotoAlbum_sol_DPhotoAlbum extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060008054600160a060020a03191633179055610e63806100326000396000f3006080604052600436106100695763ffffffff60e060020a60003504166336565372811461006e5780633b83957e1461009a57806343c73b46146100d75780634db0412c146100f857806397d0c8561461015d578063cf44959e14610184578063fce090b814610286575b600080fd5b34801561007a57600080fd5b5061009860043560243560443560ff606435811690608435166103c1565b005b3480156100a657600080fd5b506100b5600435602435610490565b6040805193845260ff9283166020850152911682820152519081900360600190f35b3480156100e357600080fd5b50610098600160a060020a0360043516610572565b34801561010457600080fd5b5061010d610755565b60408051602080825283518183015283519192839290830191858101910280838360005b83811015610149578181015183820152602001610131565b505050509050019250505060405180910390f35b34801561016957600080fd5b5061009860043560243560ff604435811690606435166107ad565b34801561019057600080fd5b506101a8600160a060020a03600435166024356108b8565b60405180806020018060200180602001848103845287818151815260200191508051906020019060200280838360005b838110156101f05781810151838201526020016101d8565b50505050905001848103835286818151815260200191508051906020019060200280838360005b8381101561022f578181015183820152602001610217565b50505050905001848103825285818151815260200191508051906020019060200280838360005b8381101561026e578181015183820152602001610256565b50505050905001965050505050505060405180910390f35b34801561029257600080fd5b5061029e600435610c24565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019060200280838360005b838110156102ea5781810151838201526020016102d2565b50505050905001858103845288818151815260200191508051906020019060200280838360005b83811015610329578181015183820152602001610311565b50505050905001858103835287818151815260200191508051906020019060200280838360005b83811015610368578181015183820152602001610350565b50505050905001858103825286818151815260200191508051906020019060200280838360005b838110156103a757818101518382015260200161038f565b505050509050019850505050505050505060405180910390f35b6000858152600160205260409020548590600160a060020a031615156103e657600080fd5b6000868152600160205260408082205481517f1d6d3b7a000000000000000000000000000000000000000000000000000000008152336004820152602481018990526044810188905260ff8088166064830152861660848201529151600160a060020a0390911692631d6d3b7a9260a4808201939182900301818387803b15801561047057600080fd5b505af1158015610484573d6000803e3d6000fd5b50505050505050505050565b600082815260016020526040812054819081908590600160a060020a031615156104b957600080fd5b6000868152600160205260408082205481517f23edf697000000000000000000000000000000000000000000000000000000008152600481018990529151600160a060020a03909116926323edf69792602480820193606093909283900390910190829087803b15801561052c57600080fd5b505af1158015610540573d6000803e3d6000fd5b505050506040513d606081101561055657600080fd5b5080516020820151604090920151909891975095509350505050565b600054600160a060020a0316331461058957600080fd5b8030600160a060020a031681600160a060020a031663d48602846040518163ffffffff1660e060020a028152600401602060405180830381600087803b1580156105d257600080fd5b505af11580156105e6573d6000803e3d6000fd5b505050506040513d60208110156105fc57600080fd5b5051600160a060020a03161461061157600080fd5b816001600084600160a060020a0316634240de2c6040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561065457600080fd5b505af1158015610668573d6000803e3d6000fd5b505050506040513d602081101561067e57600080fd5b5051815260208082019290925260409081016000908120805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0395861617905581517f4240de2c0000000000000000000000000000000000000000000000000000000081529151600294871693634240de2c93600480820194929392918390030190829087803b15801561070f57600080fd5b505af1158015610723573d6000803e3d6000fd5b505050506040513d602081101561073957600080fd5b5051815460018101835560009283526020909220909101555050565b606060028054806020026020016040519081016040528092919081815260200182805480156107a357602002820191906000526020600020905b81548152602001906001019080831161078f575b5050505050905090565b6000848152600160205260408120548590600160a060020a031615156107d257600080fd5b60008681526001602090815260408083205481517fc892a913000000000000000000000000000000000000000000000000000000008152600481018a905260ff808a166024830152881660448201529151600160a060020a039091169363c892a91393606480850194919392918390030190829087803b15801561085557600080fd5b505af1158015610869573d6000803e3d6000fd5b505050506040513d602081101561087f57600080fd5b50513360009081526003602090815260408083209983529881529781208054600181018255908252979020909601959095555050505050565b600081815260016020526040812054606091829182918291829182918291908190819081908c90600160a060020a031615156108f357600080fd5b600360008f600160a060020a0316600160a060020a0316815260200190815260200160002060008e815260200190815260200160002080548060200260200160405190810160405280929190818152602001828054801561097357602002820191906000526020600020905b81548152602001906001019080831161095f575b50505050509850600360008f600160a060020a0316600160a060020a0316815260200190815260200160002060008e8152602001908152602001600020805490506040519080825280602002602001820160405280156109dd578160200160208202803883390190505b509750600360008f600160a060020a0316600160a060020a0316815260200190815260200160002060008e815260200190815260200160002080549050604051908082528060200260200182016040528015610a43578160200160208202803883390190505b509650600360008f600160a060020a0316600160a060020a0316815260200190815260200160002060008e815260200190815260200160002080549050604051908082528060200260200182016040528015610aa9578160200160208202803883390190505b509550600091505b600360008f600160a060020a0316600160a060020a0316815260200190815260200160002060008e815260200190815260200160002080549050821015610c0f5760008d8152600160205260409020548951600160a060020a0390911690633164478f908b9085908110610b2157fe5b906020019060200201516040518263ffffffff1660e060020a02815260040180828152602001915050608060405180830381600087803b158015610b6457600080fd5b505af1158015610b78573d6000803e3d6000fd5b505050506040513d6080811015610b8e57600080fd5b50602081015160408201516060909201518a519197509195509093508590899084908110610bb857fe5b6020908102909101015286518490889084908110610bd257fe5b60ff90921660209283029091019091015285518390879084908110610bf357fe5b60ff909216602092830290910190910152600190910190610ab1565b50959c949b5092995092975050505050505050565b6000818152600160205260409020546060908190819081908590600160a060020a03161515610c5257600080fd5b6000868152600160205260408082205481517f48c311710000000000000000000000000000000000000000000000000000000081529151600160a060020a03909116926348c31171926004808201939182900301818387803b158015610cb757600080fd5b505af1158015610ccb573d6000803e3d6000fd5b505050506040513d6000823e601f3d908101601f191682016040526080811015610cf457600080fd5b810190808051640100000000811115610d0c57600080fd5b82016020810184811115610d1f57600080fd5b8151856020820283011164010000000082111715610d3c57600080fd5b50509291906020018051640100000000811115610d5857600080fd5b82016020810184811115610d6b57600080fd5b8151856020820283011164010000000082111715610d8857600080fd5b50509291906020018051640100000000811115610da457600080fd5b82016020810184811115610db757600080fd5b8151856020820283011164010000000082111715610dd457600080fd5b50509291906020018051640100000000811115610df057600080fd5b82016020810184811115610e0357600080fd5b8151856020820283011164010000000082111715610e2057600080fd5b50969d959c50939a509298509296505050505050505600a165627a7a723058205c19c5c7fb783d6b7402eecfe837aab0b8592401710bad0dccdac00176e3c6ab0029";

    public static final String FUNC_ADDPHOTOCOMMENTS = "addPhotoComments";

    public static final String FUNC_GETPHOTOCOMMENTS = "getPhotoComments";

    public static final String FUNC_ADDCATEGORY = "addCategory";

    public static final String FUNC_GETCATEGORIES = "getCategories";

    public static final String FUNC_ADDPHOTO = "addPhoto";

    public static final String FUNC_GETALLPHOTOSFORPHOTOGRAPHERBYCATEGORY = "getAllPhotosForPhotographerByCategory";

    public static final String FUNC_GETALLPHOTOSFROMCATEGORY = "getAllPhotosFromCategory";

    protected DPhotoAlbum_sol_DPhotoAlbum(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DPhotoAlbum_sol_DPhotoAlbum(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> addPhotoComments(BigInteger _category_id, BigInteger _for_photo_idx, byte[] _digest, BigInteger _hash_function, BigInteger _size) {
        final Function function = new Function(
                FUNC_ADDPHOTOCOMMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_for_photo_idx), 
                new org.web3j.abi.datatypes.generated.Bytes32(_digest), 
                new org.web3j.abi.datatypes.generated.Uint8(_hash_function), 
                new org.web3j.abi.datatypes.generated.Uint8(_size)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<byte[], BigInteger, BigInteger>> getPhotoComments(BigInteger _category_id, BigInteger _for_photo_id) {
        final Function function = new Function(FUNC_GETPHOTOCOMMENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id), 
                new org.web3j.abi.datatypes.generated.Uint256(_for_photo_id)), 
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

    public RemoteCall<TransactionReceipt> addCategory(String _category) {
        final Function function = new Function(
                FUNC_ADDCATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_category)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<List> getCategories() {
        final Function function = new Function(FUNC_GETCATEGORIES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addPhoto(BigInteger _category_id, byte[] _digest, BigInteger _hash_function, BigInteger _size) {
        final Function function = new Function(
                FUNC_ADDPHOTO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id), 
                new org.web3j.abi.datatypes.generated.Bytes32(_digest), 
                new org.web3j.abi.datatypes.generated.Uint8(_hash_function), 
                new org.web3j.abi.datatypes.generated.Uint8(_size)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>>> getAllPhotosForPhotographerByCategory(String _photographer, BigInteger _category_id) {
        final Function function = new Function(FUNC_GETALLPHOTOSFORPHOTOGRAPHERBYCATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_photographer), 
                new org.web3j.abi.datatypes.generated.Uint256(_category_id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint8>>() {}, new TypeReference<DynamicArray<Uint8>>() {}));
        return new RemoteCall<Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>>>(
                new Callable<Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>>>() {
                    @Override
                    public Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<List<byte[]>, List<BigInteger>, List<BigInteger>>(
                                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                                convertToNative((List<Uint8>) results.get(1).getValue()), 
                                convertToNative((List<Uint8>) results.get(2).getValue()));
                    }
                });
    }

    public RemoteCall<Tuple4<List<String>, List<byte[]>, List<BigInteger>, List<BigInteger>>> getAllPhotosFromCategory(BigInteger _category_id) {
        final Function function = new Function(FUNC_GETALLPHOTOSFROMCATEGORY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_category_id)), 
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

    public static RemoteCall<DPhotoAlbum_sol_DPhotoAlbum> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DPhotoAlbum_sol_DPhotoAlbum.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<DPhotoAlbum_sol_DPhotoAlbum> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DPhotoAlbum_sol_DPhotoAlbum.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static DPhotoAlbum_sol_DPhotoAlbum load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DPhotoAlbum_sol_DPhotoAlbum(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static DPhotoAlbum_sol_DPhotoAlbum load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DPhotoAlbum_sol_DPhotoAlbum(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
